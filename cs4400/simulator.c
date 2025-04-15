// CORRECT VERSION
/*
  Author: Daniel Kopta
  CS 4400, University of Utah

  * Simulator handout
  * A simple x86-like processor simulator.
  * Read in a binary file that encodes instructions to execute.
  * Simulate a processor by executing instructions one at a time and appropriately 
  * updating register and memory contents.

  * Some code and pseudo code has been provided as a starting point.

*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include "instruction.h"
#include <string.h>

// Forward declarations for helper functions
unsigned int get_file_size(int file_descriptor);
unsigned int* load_file(int file_descriptor, unsigned int size);
instruction_t* decode_instructions(unsigned int* bytes, unsigned int num_instructions);
unsigned int execute_instruction(unsigned int program_counter, instruction_t* instructions, 
				 unsigned int* registers, unsigned char* memory);
void print_instructions(instruction_t* instructions, unsigned int num_instructions);
void error_exit(const char* message);

// 17 registers
#define NUM_REGS 17
// 1024-byte stack
#define STACK_SIZE 1024

#define INT_MAX 2147483647  // 2^31 - 1
#define INT_MIN (-2147483648) // -2^31

int main(int argc, char** argv)
{
  // Make sure we have enough arguments
  if(argc < 2)
    error_exit("must provide an argument specifying a binary file to execute");

  // Open the binary file
  int file_descriptor = open(argv[1], O_RDONLY);
  if (file_descriptor == -1) 
    error_exit("unable to open input file");

  // Get the size of the file
  unsigned int file_size = get_file_size(file_descriptor);
  // Make sure the file size is a multiple of 4 bytes
  // since machine code instructions are 4 bytes each
  if(file_size % 4 != 0)
    error_exit("invalid input file");

  // Load the file into memory
  // We use an unsigned int array to represent the raw bytes
  // We could use any 4-byte integer type
  unsigned int* instruction_bytes = load_file(file_descriptor, file_size);
  close(file_descriptor);

  unsigned int num_instructions = file_size / 4;


  /****************************************/
  /**** Begin code to modify/implement ****/
  /****************************************/

  // Allocate and decode instructions (left for you to fill in)
  instruction_t* instructions = decode_instructions(instruction_bytes, num_instructions); 
  
  // Optionally print the decoded instructions for debugging
  // Will not work until you implement decode_instructions
  // Do not call this function in your handed in final version
  // print_instructions(instructions, num_instructions);
 

// Once you have completed part 1 (decoding instructions), uncomment the below block


  // Allocate and initialize registers
  unsigned int* registers = (unsigned int*)malloc(sizeof(unsigned int) * NUM_REGS);
  // TODO: initialize register values

    memset(registers, 0, sizeof(unsigned int) * NUM_REGS);
    registers[8] = 1024;
  
  // Stack memory is byte-addressed, so it must be a 1-byte type
  // TODO allocate the stack memory. Do not assign to NULL.
    unsigned char* memory = (unsigned char*)malloc(STACK_SIZE);

    memset(memory, 0, STACK_SIZE);
    
    
  // Run the simulation
  unsigned int program_counter = 0;

  // program_counter is a byte address, so we must multiply num_instructions by 4 to get the address past the last instruction
  while(program_counter != num_instructions * 4)
  {
    program_counter = execute_instruction(program_counter, instructions, registers, memory);
  }


  free(instructions);
  free(instruction_bytes);
  free(memory);
  free(registers);
  return 0;
}



/*
 * Decodes the array of raw instruction bytes into an array of instruction_t
 * Each raw instruction is encoded as a 4-byte unsigned int
*/
instruction_t* decode_instructions(unsigned int* bytes, unsigned int num_instructions)
{

  instruction_t* retval = (instruction_t*)malloc(num_instructions * sizeof(instruction_t));

  int i;
  for (i = 0; i < num_instructions; i++)
    {
      instruction_t instr;
      instr.opcode = (bytes[i] >> 27) & 0x1F;
      instr.first_register = (bytes[i] >> 22) & 0x1F;
      instr.second_register = (bytes[i] >> 17) & 0x1F;
      instr.immediate =(short)( bytes[i] & 0xFFFF);
      retval[i] = instr;
    }

 
 
  return retval;
}


/*
 * Executes a single instruction and returns the next program counter
*/
unsigned int execute_instruction(unsigned int program_counter, instruction_t* instructions, unsigned int* registers, unsigned char* memory)
{
  // program_counter is a byte address, but instructions are 4 bytes each
  // divide by 4 to get the index into the instructions array
  instruction_t instr = instructions[program_counter / 4];
  
  switch(instr.opcode)
  {
    printf("%d\n", instr.opcode);
  case subl:
    // cast the immediate up to a 32-bit signed type
    // signed overflow will not happen since one of the operands is unsigned
    registers[instr.first_register] = registers[instr.first_register] - (int)instr.immediate;
    break;
  case addl_reg_reg:
    registers[instr.second_register] = registers[instr.first_register] + registers[instr.second_register];
    break;
  case printr:
    //    printf("doing a print of %d", registers[instr.first_register]);
    printf("%d (0x%x)\n", registers[instr.first_register], registers[instr.first_register]);
    break;
  case readr:
    scanf("%d", &(registers[instr.first_register]));
    break;

  case addl_imm_reg:
    registers[instr.first_register] = (int)instr.immediate + registers[instr.first_register];
    break;
    
  case imull:
    registers[instr.second_register] = (int)registers[instr.first_register] * (int) registers[instr.second_register];
    break;

  case shrl:
    //    printf("the first register is %u\n",  registers[instr.first_register]);
    registers[instr.first_register] = registers[instr.first_register] >> 1;
    //printf("the first register is now %u\n", registers[instr.first_register]);
    
    //    unsigned int value = (unsigned int)registers[instr.first_register];
    // printf("the value is %u\n", value);
    // unsigned int shiftValue = value  >> 1;
    // registers[instr.first_register] = shiftValue;
     //    registers[instr.first_register] = shiftValue;
    // registers[instr.first_register] = (unsigned int)registers[instr.first_register] >> 1;
    
    // registers[instr.first_register] = value;
       // unsigned int shiftedVal = value >> 1;
    // registers[instr.first_register] =(unsigned int)shiftValue;
	 //      registers[instr.first_register] = registers[instr.first_register] >> 1;
    break;

  case movl_reg_reg:
    registers[instr.second_register] = registers[instr.first_register];
   
    break;


  case movl_deref_reg:
    {
      //      printf("immediate is %d and first register is %d and second register is %d and the value at the first register is %d and the value at the second register is %d  \n", (int) instr.immediate, instr.first_register,(int) instr.second_register, registers[instr.first_register], registers[instr.second_register]);
      int address = (int)instr.immediate + registers[instr.first_register];
      // printf("the address is: %d\n", address);
      int value = 0;
      // printf("the data at memory address is: %d %d %d %d\n", memory[address], memory[address + 1], memory[address+2], memory[address+3]);
      
      memcpy(&value, &memory[address], sizeof(int));
      // printf("the value will be: %d\n", value);
      registers[instr.second_register] = value;
    }
    // printf("just moved this into second register: %d\n", registers[instr.second_register]);	   
    break;

  case movl_reg_deref:
    {
      // printf("also doing this \n");
      int address = registers[instr.second_register] + (int)instr.immediate;
      // printf("the second register has %d\n", instr.second_register);
      memcpy(&memory[address], &registers[instr.first_register], sizeof(int));
      //printf("the value we just put there is now: %d %d %d %d\n", memory[address], memory[address + 1], memory[address + 2], memory[address + 3]);
    }
    //    printf("this is the first register %d\n", registers[instr.first_register]);
    // printf("this is the second register %d\n", registers[instr.second_register]);
    // printf("this is the first register %d and this is the second register %d\n", registers[instr.first_register], registers[instr.second_register]);
    //     printf("just moved this into the first register: %d\n", registers[instr.first_register]);
    break;

  case movl_imm_reg:
    //    printf("doing the move");
    registers[instr.first_register] = (int)instr.immediate;
    //  printf("just moved this into the first register: %d\n", registers[instr.first_register]);
    break;

  case cmpl:{
    // printf("doing cmpl");
    
    unsigned int reg2 = (unsigned int)registers[instr.second_register];
    unsigned int reg1 = (unsigned int)registers[instr.first_register];
    //    printf("the first register is %u and the second register is %u\n", reg1, reg2);

    int signedReg2 = (int)reg2;
    int signedReg1 = (int)reg1;

    long longReg1 = (long)signedReg1;
    long longReg2 = (long)signedReg2;

    long longResult = longReg2 - longReg1;
    
    int result = reg2 - reg1;
    // printf("the result is %u\n", result);
    registers[0] = 0;

    if ((unsigned int)reg2 < (unsigned int)reg1)
      {
	registers[0] = registers[0] | (1 << 0);
      }

    if (result == 0)
      {
	registers[0] = registers[0] | (1 << 6);
      }

    if (result < 0)
      {
	registers[0] = registers[0] | (1 << 7);
      }

    if (longResult > INT_MAX || longResult < INT_MIN)
      {
	registers[0] = registers[0] | (1 << 11);
      }

    break;
    
  }

  case je:
    if (registers[0] & (1 << 6))
      return instr.immediate + program_counter + 4;
    break;

  case jl:
    if (((registers[0] >> 7) & 1) != ((registers[0] >> 11) & 1))
      return instr.immediate + 4 + program_counter;
    break;

  case jle:
    if ((registers[0] & (1 << 6)) || (((registers[0] >> 7) & 1) != ((registers[0] >> 11) & 1)))
      return instr.immediate + 4 + program_counter;
    break;

  case jge:
    if (((registers[0] >> 7) & 1) == ((registers[0] >> 11) & 1))
      return instr.immediate + 4 + program_counter;
    break;

  case jbe:
    if ((registers[0] & (1 << 0)) || (registers[0] & (1 << 6)))
      return instr.immediate + 4 + program_counter;
    break;

  case jmp:
    return program_counter + 4 + instr.immediate;
    
  case call:
    registers[8] -= 4;
    
    *(unsigned int *)(memory + registers[8]) = program_counter+4;
    return instr.immediate + 4 + program_counter;

  case ret:
    if (registers[8] == 1024){
      exit(0);
    }
    else
      {
	program_counter = *(unsigned int *)(memory + registers[8]);
	registers[8] += 4;
	return program_counter;
      }
    break;
  
  case pushl:
    registers[8] -= 4;
    *(unsigned int *)(memory + registers[8]) = registers[instr.first_register];
    break;

  case popl:
    registers[instr.first_register] = *(unsigned int *)(memory + registers[8]);
    registers[8] += 4;
    break;

  
				     


  // TODO: Implement remaining instructions

  }

  // TODO: Don't always return program_counter + 4
  //       Some instructions will jump elsewhere

  // program_counter + 4 represents the subsequent instruction
  return program_counter + 4;
}


/***********************************************/
/**** Begin helper functions. Do not modify ****/
/***********************************************/

/*
 * Returns the file size in bytes of the file referred to by the given descriptor
*/
unsigned int get_file_size(int file_descriptor)
{
  struct stat file_stat;
  fstat(file_descriptor, &file_stat);
  return file_stat.st_size;
}

/*
 * Loads the raw bytes of a file into an array of 4-byte units
*/
unsigned int* load_file(int file_descriptor, unsigned int size)
{
  unsigned int* raw_instruction_bytes = (unsigned int*)malloc(size);
  if(raw_instruction_bytes == NULL)
    error_exit("unable to allocate memory for instruction bytes (something went really wrong)");

  int num_read = read(file_descriptor, raw_instruction_bytes, size);

  if(num_read != size)
    error_exit("unable to read file (something went really wrong)");

  return raw_instruction_bytes;
}

/*
 * Prints the opcode, register IDs, and immediate of every instruction, 
 * assuming they have been decoded into the instructions array
*/
void print_instructions(instruction_t* instructions, unsigned int num_instructions)
{
  printf("instructions: \n");
  unsigned int i;
  for(i = 0; i < num_instructions; i++)
  {
    printf("op: %d, reg1: %d, reg2: %d, imm: %d\n", 
	   instructions[i].opcode,
	   instructions[i].first_register,
	   instructions[i].second_register,
	   instructions[i].immediate);
  }
  printf("--------------\n");
}


/*
 * Prints an error and then exits the program with status 1
*/
void error_exit(const char* message)
{
  printf("Error: %s\n", message);
  exit(1);
}
