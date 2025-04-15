/// This class is the view for our snake game.
/// It communicates with the controller and draws our objects.
/// 27 November 2023
/// Neeraja Vasa and Natalie Wilkins




using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using IImage = Microsoft.Maui.Graphics.IImage;
#if MACCATALYST
using Microsoft.Maui.Graphics.Platform;
#else
using Microsoft.Maui.Graphics.Win2D;
#endif
using Color = Microsoft.Maui.Graphics.Color;
using System.Reflection;
using Microsoft.Maui;
using System.Net;
using Font = Microsoft.Maui.Graphics.Font;
using SizeF = Microsoft.Maui.Graphics.SizeF;
using Model;
using Windows.UI.Input.Inking;
using GameController;
using Windows.Security.Cryptography.Certificates;
using Microsoft.UI.Xaml.Media;
using Windows.UI.Text;

namespace SnakeGame;
public class WorldPanel : IDrawable
{
    private IImage wall;
    private IImage background;
    public delegate void ObjectDrawer(object o, ICanvas canvas);
    
    // instance variables so we can track tihngs
    GameControl gc;
    World world;

    // default size but will be changed if server wants a different size
    int size = 2000;
    int ID;




    private bool initializedForDrawing = false;

    private IImage loadImage(string name)
    {
        Assembly assembly = GetType().GetTypeInfo().Assembly;
        string path = "SnakeClient.Resources.Images";
        using (Stream stream = assembly.GetManifestResourceStream($"{path}.{name}"))
        {
#if MACCATALYST
            return PlatformImage.FromStream(stream);
#else
            return new W2DImageLoadingService().FromStream(stream);
#endif
        }
    }

    public WorldPanel()
    {

    }

    /// <summary>
    /// This uses an event handler to set the size of the world.
    /// It is invoked in the GameController when it receives size and ID.
    /// </summary>
    /// <param name="size"></param>
    public void SetSize(int size)
    {
        this.size = size;

    }

    /// <summary>
    /// This uses an event handler to set the ID of the this snake.
    /// It is invoked in the GameController when it receives size and ID.
    /// </summary>
    /// <param name="ID"></param>
    public void SetID(int ID)
    {
        this.ID = ID;
    }

    private void InitializeDrawing()
    {
        wall = loadImage("wallsprite.png");
        background = loadImage("otherbackground.png"); // different background than given
        initializedForDrawing = true;
    }

    public void Draw(ICanvas canvas, RectF dirtyRect)
    {
        if (!initializedForDrawing)
            InitializeDrawing();

        // undo previous transformations from last frame
        canvas.ResetState();

       
        // check if we actually have a world
        if (world!= null) { 

            // don't let it be changed while we're in it
        lock(world)
        {


                    // keep track of head of this snake x and y
                    float playerX, playerY;

                // if we have a snake in the dictionary then we'll get its x and y, otherwise assign them to 1
                    if (world.snakes.ContainsKey(ID))
                    {
                        int playerBodyLength = world.snakes[ID].body.Count;
                        playerX = (float)world.snakes[ID].body[playerBodyLength - 1].X;
                        playerY = (float)world.snakes[ID].body[playerBodyLength - 1].Y;
                    }
                    else
                    {
                        playerX = 1;
                        playerY = 1;
                    }

                    // translate the canvas, draw the background
                    canvas.Translate(-playerX + (900 / 2), -playerY + (900 / 2));
                    canvas.DrawImage(background, -size / 2, -size / 2, size, size);


                // iterations through each dictionary of objects, draw them with the helper method


                    foreach (Powerup power in world.powerups.Values)
                    {
                        if (power.died == false)
                        {
                            DrawObjectWithTransform(canvas, power, power.x, power.y, 0, PowerupDrawer);
                        }
                    }

                    foreach (Wall wall in world.walls.Values)
                    {
                        int offset = 0;
                        if (wall.isHorizontal == true)
                        {
                            for (int i = 0; i < wall.segmentCount; i++) // do each segment
                            {
                                DrawObjectWithTransform(canvas, wall, wall.p1.X + offset, wall.p1.Y, 0, WallDrawer);
                                offset += 50;
                            }
                        }
                        else
                        {
                            for (int i = 0; i < wall.segmentCount; i++) // do each segment
                            {
                                DrawObjectWithTransform(canvas, wall, wall.p1.X, wall.p1.Y + offset, 0, WallDrawer);
                                offset += 50;
                            }
                        }
                    }

                    // iterate through snakes

                    foreach (Snake snake in world.snakes.Values)
                    {
                    if (snake.alive != true)
                    {
                        continue; // don't think this has actually been working, but we tried lol
                    }
                        
                        for (int i = 0; i < snake.body.Count - 1; i++)
                        {
                            ColorDecider(snake.snake, canvas);
                       

                        // set up our vector
                         Vector2D segmentLength =  snake.body[i + 1] - snake.body[i]; 

                         double length = segmentLength.Length();

                        segmentLength.Normalize();

                        float angle = segmentLength.ToAngle();

                        DrawObjectWithTransform(canvas, length, snake.body[i].X , snake.body[i].Y , angle , SnakeDrawer);

                       // check cases to draw rectangle
                        if (angle == -90 && snake.body.Count != 2)
                        {
                            canvas.FillRectangle((float)snake.body[i].X , (float)snake.body[i].Y - 5, (float)5.0, (float)10.0);
                        }
                        if (angle == 90 && snake.body.Count != 2)
                        {
                            canvas.FillRectangle((float)snake.body[i].X - 5, (float)snake.body[i].Y - 5, (float)5.0, (float)10.0);
                        }
                        if (angle == 0 && snake.body.Count != 2)
                        {
                            canvas.FillRectangle((float)snake.body[i].X - 5, (float)snake.body[i].Y, (float)10.0, (float)5.0);
                        }
                        if (angle == 180 && snake.body.Count != 2)
                        {
                            canvas.FillRectangle((float)snake.body[i].X - 5, (float)snake.body[i].Y - 5, (float)10.0, (float)5.0);
                        }

                    }
                        // draw name and score at head 
                        DrawObjectWithTransform(canvas, snake, snake.body[snake.body.Count - 1].X, snake.body[snake.body.Count - 1].Y, 0, NameAndScoreDrawer);


                    }
                }
        }
        
    }

    /// <summary>
    /// Draw the name and score of each snake. 
    /// </summary>
    /// <param name="o"></param>
    /// <param name="canvas"></param>
    private void NameAndScoreDrawer(object o, ICanvas canvas)
    {
        Snake snak = (Snake)o;
        canvas.Font = new Font("Harrington", 30); // fancy font
        canvas.FontSize = 15;
       // canvas.Font = Font.DefaultBold;
        canvas.DrawString(snak.name + "\n" + snak.score, 0,0, HorizontalAlignment.Center);
    }

    /// <summary>
    /// simple wall drawer
    /// </summary>
    /// <param name="o"></param>
    /// <param name="canvas"></param>
    private void WallDrawer(object o, ICanvas canvas)
    {
        canvas.DrawImage(wall, -25, -25, 50, 50);

    }

    /// <summary>
    /// simple background drawer
    /// </summary>
    /// <param name="o"></param>
    /// <param name="canvas"></param>
    private void BackGroundDrawer(object o, ICanvas canvas)
    {
        canvas.DrawImage(background, 0, 0, 900, 900);
    }


    /// <summary>
    /// simple powerup drawer
    /// </summary>
    /// <param name="o"></param>
    /// <param name="canvas"></param>
    private void PowerupDrawer(object o, ICanvas canvas)
    {
        canvas.FillColor = Colors.Coral;
        canvas.FillRectangle(-8, -8, 16, 16);
    }


    /// <summary>
    /// snake drawer
    /// </summary>
    /// <param name="o"></param>
    /// <param name="canvas"></param>
    private void SnakeDrawer(object o, ICanvas canvas)
    {
        double num = (double) o;
        canvas.StrokeSize = 10;
        canvas.DrawLine(0, 0, 0, (int)-num);
    }


    /// <summary>
    /// This is called to make sure that each of the first 8 snakes has a different color. 
    /// </summary>
    /// <param name="ID"></param>
    /// <param name="canvas"></param>
    private void ColorDecider(int ID, ICanvas canvas)
    {
        // draw a line
        canvas.FillColor = Colors.Aquamarine;
        canvas.StrokeColor = Colors.Aquamarine;

        if (ID % 8 == 0)
        {
            canvas.FillColor = Colors.HotPink;
            canvas.StrokeColor = Colors.HotPink;

        }
        else if (ID % 8 == 1)
        {
            canvas.FillColor = Colors.Purple;
            canvas.StrokeColor = Colors.Purple;

        }
        else if (ID % 8 == 2)
        {
            canvas.FillColor = Colors.RoyalBlue;
            canvas.StrokeColor = Colors.RoyalBlue;

        }

        else if (ID % 8 == 3)
        {
            canvas.FillColor = Colors.Chocolate;
            canvas.StrokeColor = Colors.Chocolate;

        }
        else if (ID % 8 == 4)
        {
            canvas.FillColor = Colors.DarkGreen;
            canvas.StrokeColor = Colors.DarkGreen;

        }
        else if (ID % 8 == 5)
        {
            canvas.FillColor = Colors.DarkBlue;
            canvas.StrokeColor = Colors.DarkBlue;

        }
        else if (ID % 8 == 6)
        {
            canvas.FillColor = Colors.Yellow;
            canvas.StrokeColor = Colors.Yellow;

        }
        else if (ID % 8 == 7)
        {
            canvas.FillColor = Colors.Coral;
            canvas.StrokeColor = Colors.Coral;
        }

    }


    /// <summary>
    /// This method performs a translation and rotation to draw an object.
    /// </summary>
    /// <param name="canvas">The canvas object for drawing onto</param>
    /// <param name="o">The object to draw</param>
    /// <param name="worldX">The X component of the object's position in world space</param>
    /// <param name="worldY">The Y component of the object's position in world space</param>
    /// <param name="angle">The orientation of the object, measured in degrees clockwise from "up"</param>
    /// <param name="drawer">The drawer delegate. After the transformation is applied, the delegate is invoked to draw whatever it wants</param>
    private void DrawObjectWithTransform(ICanvas canvas, object o, double worldX, double worldY, double angle, ObjectDrawer drawer)
    {
        // "push" the current transform
        canvas.SaveState();

        canvas.Translate((float)worldX, (float)worldY);
        canvas.Rotate((float)angle);
        drawer(o, canvas);

        // "pop" the transform
        canvas.RestoreState();
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public void setGameController(GameControl gc)
    {
        this.gc = gc;
    }
}