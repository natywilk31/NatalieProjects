/// This is the wall object for our world.
/// We use a segment count to see how many wall pictures we need to draw in the view.
/// 27 November 2023
/// Neeraja Vasa and Natalie Wilkins


using SnakeGame;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Model
{
    [DataContract(Name = "Wall" , Namespace = "")]
    public class Wall
    {
        [JsonPropertyName("wall")]
        public int wall { get; set; }
        [DataMember(Name = "p1")]
        public Vector2D? p1 { get; set; }
        [DataMember(Name = "p2")]
        public Vector2D? p2 { get; set; }
        private int wallID = 0;


        [JsonIgnore]
        public bool isHorizontal;
        [JsonIgnore]
        public int segmentCount;

        
        public Wall()
        {
            this.wall = -1;
            this.p1 = new Vector2D();
            this.p2 = new Vector2D();
            
        }

        [JsonConstructor]
        public Wall(int wall , Vector2D p1, Vector2D p2)
        {
            this.wall = wall;

            this.p1 = p1;
            this.p2 = p2;
            initialization();
        }

        // to get our segment count!! and check if horizontal or vertical
        private void initialization()
        {
            if (p1.X == p2.X)
            {
                // want a to be the bottom or left point
                if (p1.Y > p2.Y)
                {
                    (p1, p2) = (p2, p1);
                }
                segmentCount = (int)(p2!.Y - p1!.Y) / 50 + 1;
                isHorizontal = false;
            }
            else
            {
                // force a to be bottom or left
                if (p1.X > p2.X)
                {
                    (p1, p2) = (p2, p1);
                }
                segmentCount = (int)(p2.X - p1.X) / 50 + 1;
                isHorizontal = true;
            }
        }
    }
}