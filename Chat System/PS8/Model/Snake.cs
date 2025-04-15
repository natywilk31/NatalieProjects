/// This is the snake for our model. It has lots of member variables, which we receive in JSON. 
/// 27 November 2023
/// Neeraja Vasa and Natalie Wilkins


using SnakeGame;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Model
{
    public class Snake
    {
        [JsonInclude]
        public int snake { get; set; }
        [JsonInclude]
        public string? name { get; set; }
        [JsonInclude]
        public List<Vector2D>? body { get; set; }
        [JsonInclude]
        public Vector2D? dir { get; set; }
        [JsonInclude]
        public int score { get; set; }
        [JsonInclude]
        public bool died { get; set; }
        [JsonInclude]
        public bool alive { get; set; }
        [JsonInclude]
        public bool dc { get; set; }
        [JsonInclude]
        public bool join = false;

        [JsonIgnore]
        public bool isHorizontal;



    [JsonConstructor]
        public Snake(int snake , string name , List<Vector2D> body , Vector2D dir , int score, bool died, bool alive, bool dc, bool join)
        {
            // set everything we get
            this.snake = snake;
            this.name = name;

            this.body = body;

            if (body.Count == 0)
            {
                alive = false;
                died = true;
            }
            else
            {
                alive = true;
                died = false;
            }

            this.dir = dir;
            if (dir.X == 0)
                isHorizontal = false;
            else
                isHorizontal = true;

            // lots of setting
            this.score = score;
            this.died = died;
            this.alive = alive;
            this.dc = dc;
            this.join = join;
        }



    }
}
