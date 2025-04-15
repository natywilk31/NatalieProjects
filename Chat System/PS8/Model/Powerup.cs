/// This is the powerup object. We receive powerups from the controller in JSON.
/// 27 November 2023
/// Neeraja Vasa and Natalie Wilkins




using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using SnakeGame;

namespace Model
{
    public class Powerup
    {
        [JsonInclude]
        public Vector2D loc { get; set; }
        [JsonInclude]
        public int power { get; set; }
        [JsonIgnore]
        public double x { get; set; }
        [JsonIgnore]
        public double y { get; set; }
        [JsonInclude]
        public bool died { get; set; }


        [JsonConstructor]
        public Powerup(int power, Vector2D loc, bool died)
        {
            this.loc = loc;
            this.power = power;
            x = loc.X;
            y = loc.Y;
            this.died = died;
        }
    }
}
