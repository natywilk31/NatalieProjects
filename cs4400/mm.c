/*
Natalie Wilkins - CS4400 Spring 2025
u1303426

This uses an explicit free list, coalesce, and unmaps free chunks
in order to optimize throughput.
 */
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
#include <string.h>
#include "mm.h"
#include "memlib.h"

// structs for block header and footer as well as a node and chunk info
typedef struct {
  size_t size;
  char allocated;
} block_header;

typedef struct {
  size_t size;
  int filler;
} block_footer;

typedef struct node {
  struct node* next;
  struct node* prev;
} node;

typedef struct {
  size_t size;
  int filler;
} info;

node* first_free;
void* bp;
node* last_chunk_ptr;
size_t PAGE_SIZE;
size_t pages_used;
size_t chunks_used;

// mix of given and custom macros and forward declarations
#define ALIGNMENT 16
#define ALIGN(size) (((size) + (ALIGNMENT-1)) & ~(ALIGNMENT-1))
#define PAGE_ALIGN(size) (((size) + (mem_pagesize()-1)) & ~(mem_pagesize()-1))
#define HDRP(bp) ((char*) (bp) - sizeof(block_header))
#define FTRP(bp) ((char*) (bp) + GET_SIZE(HDRP(bp)) - BLOCK_OVERHEAD)
#define GET_SIZE(p) ((block_header*)(p))->size
#define GET_ALLOC(p) ((block_header*)(p))->allocated
#define NEXT_BLKP(bp) ((char*)(bp) + GET_SIZE(HDRP(bp)))
#define PREV_BLKP(bp) ((char*)(bp) - GET_SIZE((char*)(bp) - BLOCK_OVERHEAD))
void* coalesce(void* bp);
void set_allocated(void* bp, size_t size);
void* set_new_chunk(size_t size);
void* init_free_block(node* first_chunk, size_t size);
void replace_node(node* new_node, node* old_node);
void remove_node(node* node);
void add_to_free_list(node* bp);
#define BLOCK_OVERHEAD (sizeof(block_header) + sizeof(block_footer))
#define CHUNK_OVERHEAD (sizeof(node) + sizeof(block_footer) + (2*sizeof(block_header)) + sizeof(info))

/*
Initializer. sets everything back to base values
 */
int mm_init(void)
{
  first_free = NULL;
  bp = NULL;
  last_chunk_ptr = NULL;
  PAGE_SIZE = 0;
  pages_used = 0;
  chunks_used = 0;
  PAGE_SIZE = mem_pagesize();
  set_new_chunk(PAGE_SIZE);
  return 0;
}

/*
  allocate the given size of memory
 */
void *mm_malloc(size_t size)
{
  size_t need_size = (size > sizeof(node)) ? size : sizeof(node);
  size_t new_size = ALIGN(need_size + BLOCK_OVERHEAD);
  node* fp = first_free;
  node* big_fp = NULL; 

  if(first_free){
    while(1){
      if(GET_SIZE(HDRP((void*)fp)) >= new_size){
          big_fp = fp;
          break;
      }
      if(fp->next == NULL)
        break;
      fp = fp->next;
    }
  }

  if(big_fp){
    set_allocated((void*)big_fp, new_size);
    return (void*)big_fp;
  }

  void* bp = set_new_chunk(new_size);
  set_allocated(bp, new_size);

  return bp;
}

/*
  free up space that isn't being used anymore
 */
void mm_free(void* bp)
{
  GET_ALLOC(HDRP(bp)) = 0;
  void* fp = coalesce(bp);

  if(pages_used > 20)
    {
      size_t free_sz = GET_SIZE(HDRP(fp));

      if (GET_SIZE(HDRP(PREV_BLKP(fp))) == (sizeof(block_header) + sizeof(block_footer))){
	info* infos = (info*)(PREV_BLKP(fp) - sizeof(block_header) - sizeof(info));
	size_t chunk_size = infos->size;

	if(free_sz == (chunk_size - CHUNK_OVERHEAD))
	  {
	    remove_node(fp);
	    void* chunk_ptr = (void*)infos - sizeof(node);
	    remove_node(chunk_ptr);
	    mem_unmap(chunk_ptr, chunk_size);
	    chunks_used--;
	    pages_used -= (chunk_size / PAGE_SIZE);
	  }
      }
    }  
}
    
/* 
   if there are two free blocks next to each other, merge them
   and then it updates the linked list
 */
   void* coalesce(void* bp){
    size_t prev_alloc = GET_ALLOC(HDRP(PREV_BLKP(bp)));
    size_t next_alloc = GET_ALLOC(HDRP(NEXT_BLKP(bp)));
    size_t size = GET_SIZE(HDRP(bp));
    size_t new_size = size;

    if(prev_alloc && next_alloc){ // Case 1
      add_to_free_list((node*)bp);
    }

    else if(prev_alloc && !next_alloc){ // Case 2
      replace_node((node*)bp, (node*)NEXT_BLKP(bp));
      new_size = size + GET_SIZE(HDRP(NEXT_BLKP(bp)));
      GET_SIZE(HDRP(bp)) = new_size;
      GET_SIZE(FTRP(bp)) = new_size;
    }

    else if(!prev_alloc && next_alloc){ // Case 3
      new_size = size + GET_SIZE(HDRP(PREV_BLKP(bp)));
      GET_SIZE(FTRP(bp)) = new_size;
      GET_SIZE(HDRP(PREV_BLKP(bp))) = new_size;
      bp = PREV_BLKP(bp);
    }

    else{ // Case 4
      remove_node((node*)NEXT_BLKP(bp));
      new_size = size + (GET_SIZE(HDRP(PREV_BLKP(bp))) + GET_SIZE(HDRP(NEXT_BLKP(bp))));
      GET_SIZE(FTRP(NEXT_BLKP(bp))) = new_size;
      GET_SIZE(HDRP(PREV_BLKP(bp))) = new_size;
      bp = PREV_BLKP(bp);
    }
    return bp;
  }

/*
  marks a memory block as allocated and splits the block and updates the
free list, if no splitting then marks the block as allocated and 
gets rid of it on the free list
 */
void set_allocated(void* bp, size_t size){
  size_t extra_size = GET_SIZE(HDRP(bp)) - size;
  if(extra_size > ALIGN(sizeof(node) + BLOCK_OVERHEAD)){
    GET_SIZE(HDRP(bp)) = size;
    GET_SIZE(FTRP(bp)) = size;
    GET_SIZE(HDRP(NEXT_BLKP(bp))) = extra_size;
    GET_SIZE(FTRP(NEXT_BLKP(bp))) = extra_size;
    GET_ALLOC(HDRP(NEXT_BLKP(bp))) = 0;
    GET_ALLOC(HDRP((bp))) = 1;

    // Adjust free list pointers
    replace_node((node*)NEXT_BLKP(bp), (node*)bp);

  }
  else{
    GET_ALLOC(HDRP(bp)) = 1;
    remove_node((void*)bp);
  }
}

/*
  set up a new chunk when more space is needed
 */
void* set_new_chunk(size_t size)
{
  size_t pages_requested = PAGE_ALIGN(size) / PAGE_SIZE;
  size_t new_pages_requested = (pages_requested > pages_used) ? pages_requested : pages_used;

  if(new_pages_requested > 32)
    new_pages_requested = 32;
  pages_used += new_pages_requested;
  size_t chunk_size = new_pages_requested * PAGE_SIZE;

  node* chunk_ptr = mem_map(chunk_size);

  if(last_chunk_ptr){
    last_chunk_ptr->next = chunk_ptr;
    chunk_ptr->prev = last_chunk_ptr;
    last_chunk_ptr = chunk_ptr;
    assert(chunk_ptr->prev != chunk_ptr);
    chunk_ptr->next = NULL;
  }
  else{
    chunk_ptr->next = chunk_ptr->prev = NULL;
    last_chunk_ptr = chunk_ptr;
  }

  info* info_ptr = (void*)chunk_ptr + ALIGN(sizeof(node));
  info_ptr->size = chunk_size;

  void* terminator_ptr = (void*)chunk_ptr + ALIGN(sizeof(node) + sizeof(info) + sizeof(block_header));
  GET_SIZE(HDRP(terminator_ptr)) = ALIGN(sizeof(block_header) + sizeof(block_footer));
  GET_ALLOC(HDRP(terminator_ptr)) = 1;
  GET_SIZE(FTRP(terminator_ptr)) = ALIGN(sizeof(block_header) + sizeof(block_footer));
  GET_ALLOC(FTRP(terminator_ptr)) = 1;

  terminator_ptr = (void*)chunk_ptr + chunk_size;
  GET_SIZE(HDRP(terminator_ptr)) = 0;
  GET_ALLOC(HDRP(terminator_ptr)) = 1;

  size_t free_block_size = chunk_size - CHUNK_OVERHEAD;
  void* bp = init_free_block(chunk_ptr, free_block_size);

  chunks_used++;
  return bp;
}

/*
  set up a new free block, add it to the list
 */
void* init_free_block(node* first_chunk, size_t size){
  void* bp = (void*)first_chunk + CHUNK_OVERHEAD;

  GET_SIZE(HDRP(bp)) = size;
  GET_ALLOC(HDRP(bp)) = 0;
  GET_SIZE(FTRP(bp)) = size;
  GET_ALLOC(FTRP(bp)) = 0;
  add_to_free_list((node*)bp);

  return bp;
}

/*
  replace a node, rearrange the list to account for it
 */
void replace_node(node* new_node, node* old_node){
  new_node->next = old_node->next;
  new_node->prev = old_node->prev;

  if(old_node->prev)
    old_node->prev->next = new_node;
  if(old_node->next)
    old_node->next->prev = new_node;

  if(old_node == first_free)
    first_free = new_node;
}

/*
  remove a node, update the surrounding nodes to account for it
*/
void remove_node(node* node){
  if(node == first_free){
    if(node->next){
      node->next->prev = NULL;
      first_free = node->next;
    }
    else
      first_free = NULL;
  }

  else if(node == last_chunk_ptr){
    if(node->prev){
      node->prev->next = NULL;
      last_chunk_ptr = node->prev;
    }
    else
      last_chunk_ptr = NULL;
  }

  else{
    if(node->prev && node->next){
      node->prev->next = node->next;
      node->next->prev = node->prev;
    }
    else if(node->next)
      node->next->prev = NULL;
    else if(node->prev)
      node->prev->next = NULL;
  }
}

/*
  adds a node to the free list. 
 */
void add_to_free_list(node* fp){
  if(first_free){
    first_free->prev = fp;
    fp->next = first_free;
  }
  else{
    fp->next = NULL;
  }
  fp->prev = NULL;
  first_free = fp;
}
