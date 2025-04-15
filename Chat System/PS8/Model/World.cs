/// This is the base of our model
/// It has dictionaries that hold our objects.
/// It will be updated as we receive information in the form of JSON through the controller. 
/// 27 November 2023
/// Neeraja Vasa and Natalie Wilkins


using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using SnakeGame;

namespace Model
{
    public class World
    {
        // dictionaries of our things
        public Dictionary<int, Powerup> powerups;
        public Dictionary<int, Snake> snakes;
        public Dictionary<int, Wall> walls;


        [JsonConstructor]
        public World()
        {
            powerups = new();
            snakes = new();
            walls = new();

            
        }


    }
}