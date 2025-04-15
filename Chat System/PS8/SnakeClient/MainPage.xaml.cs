/// This is the framework for our view.
/// It will have our WorldPanel on top of it as the graphics view. 
/// 27 November 2023
/// Neeraja Vasa and Natalie Wilkins



using System.Reflection;
using Model;
using Windows.Gaming.Input;
using GameController;
using Windows.Security.Cryptography.Core;
using System.Text.Json;
using System.Diagnostics;


namespace SnakeGame;



public partial class MainPage : ContentPage
{

    public delegate void ObjectDrawer(object o, ICanvas canvas);
    public GameControl gameController = new();
    bool errorHappened = false;



    public MainPage()
    {
        //Added for moving snakes

        gameController.GotSize += SetSize;
        gameController.GotID += SetID;
        gameController.MessagesArrived += OnFrame;
        gameController.Error += ErrorOnConnect;
        InitializeComponent();
        graphicsView.Invalidate();
        worldPanel.setWorld(gameController.world);
        worldPanel.setGameController(gameController);

        // add some event subscriptions here
        gameController.MessagesArrived += OnFrame;

       

    }

    // a method we made because of our long trial of trying to figure out connect errors
    private void ErrorOnConnect(string err)
    {
        Dispatcher.Dispatch(() => {
        DisplayAlert("Connection failed.", "There was an issue trying to connect.", "OK");
        connectButton.IsEnabled = true;
        errorHappened = true; }
        );
    }

    // we need our ID in order to draw and center our snake
    private void SetID(int ID)
    {
        worldPanel.SetID(ID);
    }

    // grab size when server sends it to our controller and use it for our view
    private void SetSize(int size)
    {
        HeightRequest = size;
        WidthRequest = size;
        worldPanel.SetSize(size);
    }

    void OnTapped(object sender, TappedEventArgs e)
    {
        keyboardHack.Focus();
    }

    // to send back to server  for movement
    void OnTextChanged(object sender, EventArgs e)
    {
        Entry entry = (Entry)sender;
        String text = entry.Text.ToLower();
        if (text == "w")
        {
            gameController.Send("\n" + "{\"moving\":\"up\"}" + "\n");
        }
        else if (text == "a")
        {
            gameController.Send("\n" + "{ \"moving\":\"left\"}" + "\n");
        }
        else if (text == "s")
        {
            gameController.Send("\n" + "{ \"moving\":\"down\"}" + "\n");
        }
        else if (text == "d")
        {
            gameController.Send("\n" + "{ \"moving\":\"right\"}" + "\n");
        }
        else
            gameController.Send("\n" + "{ \"moving\":\"none\"}" + "\n");
        entry.Text = "";
    }



    /// <summary>
    /// A method that can be used as an ObjectDrawer delegate
    /// </summary>
    /// <param name="o">The powerup to draw</param>
    /// <param name="canvas"></param>




    /// more trying to figure out connect errors
    private void NetworkErrorHandler()
    {
        DisplayAlert("Error", "Disconnected from server", "OK");
        connectButton.IsEnabled = true;
        errorHappened = true;
    }


    /// <summary>
    /// Event handler for the connect button
    /// We will put the connection attempt interface here in the view.
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="args"></param>
    private void ConnectClick(object sender, EventArgs args)
    {

        if (serverText.Text == "")
        {
            DisplayAlert("Error", "Please enter a server address", "OK");
            return;
        }
        if (nameText.Text == "")
        {
            DisplayAlert("Error", "Please enter a name", "OK");
            return;
        }
        if (nameText.Text.Length > 16)
        {
            DisplayAlert("Error", "Name must be less than 16 characters", "OK");
            return;
        }

        
       // try to connect, if not then display an alert and let the user try again

        try
        {
            errorHappened = false;
            gameController.SetName(nameText.Text);
            gameController.Connect(serverText.Text);
            worldPanel.setWorld(gameController.world);
            connectButton.IsEnabled = false;

        }
        catch (Exception)
        {
            DisplayAlert("Connection failed.", "There was an issue trying to connect.", "OK");
            connectButton.IsEnabled = true;
            return;

        }

        if (errorHappened == true)
        {
            connectButton.IsEnabled = true;
            return;
        }
        
        keyboardHack.Focus();

    }

    /// <summary>
    /// Use this method as an event handler for when the controller has updated the world
    /// </summary>
    public void OnFrame()
    {
        Dispatcher.Dispatch(() => graphicsView.Invalidate());
    }

  



    private void ControlsButton_Clicked(object sender, EventArgs e)
    {
        DisplayAlert("Controls",
                     "W:\t\t Move up\n" +
                     "A:\t\t Move left\n" +
                     "S:\t\t Move down\n" +
                     "D:\t\t Move right\n",
                     "OK");
    }

    private void AboutButton_Clicked(object sender, EventArgs e)
    {
        DisplayAlert("About",
      "SnakeGame solution\nArtwork by Jolie Uk and Alex Smith, as well as Nic Shi on Unsplash \nGame design by Daniel Kopta and Travis Martin\n" +
      "Implementation by Neeraja Vasa and Natalie Wilkins\n" +
        "CS 3500 Fall 2022, University of Utah", "OK");
    }

    private void ContentPage_Focused(object sender, FocusEventArgs e)
    {
        if (!connectButton.IsEnabled)
            keyboardHack.Focus();
    }


}