/// This is the controller part of our MVC project.
/// It uses the given Networking DLL.
/// 27 November 2023
/// Neeraja Vasa and Natalie Wilkins



namespace GameController

{
    using NetworkUtil;
    using System.Text.RegularExpressions;
    using Model;
    using System.Text.Json;
    using System.Diagnostics;
    using System;
    using System.Xml.Linq;

    public class GameControl
    {

        // Controller events that the view can subscribe to
        public delegate void MessageHandler();
        public event MessageHandler? MessagesArrived;

        public delegate void ConnectedHandler();
        public event ConnectedHandler? Connected;

        public delegate void ErrorHandler(string err);
        public event ErrorHandler? Error;

        public delegate void SizeReceived(int size);
        public event SizeReceived? GotSize;

        public delegate void IDReceived(int ID);
        public event IDReceived? GotID;

        public bool errorHappened;

        /// <summary>
        /// State representing the connection with the server
        /// </summary>
        SocketState? theServer = null;


        // controller will hold the world we use
        public World world = new World();

        public int size;

        public string name;

        /// <summary>
        /// Begins the process of connecting to the server
        /// </summary>
        /// <param name="addr"></param>
        public void Connect(string addr)
        {
            Networking.ConnectToServer(OnConnect, addr, 11000);
        }


        /// <summary>
        /// Method to be invoked by the networking library when a connection is made
        /// </summary>
        /// <param name="state"></param>
        private void OnConnect(SocketState state)
        {
            if (state.ErrorOccurred)
            {
                // inform the view
                Error?.Invoke("Error connecting to server");
                errorHappened = true;
                //throw new Exception();
                return;
            }

            // inform the view
            Connected?.Invoke();

            theServer = state;

            // Start an event loop to receive messages from the server
            state.OnNetworkAction = GetSizeAndID;
            Debug.WriteLine("Subscribed to receive message");

            bool v = Networking.Send(state.TheSocket, name + "\n");
            Networking.GetData(state);
        }

        // receiving the name and ID from the server, then will change callback to our normal receive messages
        private void GetSizeAndID(SocketState state)
        {
            string data = state.GetData();
            string[] parts = Regex.Split(data, @"(?<=[\n])");

            //GotSize?.Invoke(Int32.Parse(parts[1]));
            size = Int32.Parse(parts[1]);
            int ID = Int32.Parse(parts[0]);
            GotID?.Invoke(ID);

            //state.RemoveData(0, state.GetData().Length);
            state.RemoveData(0, parts[0].Length);
            state.RemoveData(0, parts[1].Length);


            state.OnNetworkAction = ReceiveMessage;

            Networking.GetData(state);
        }

       
        /// <summary>
        /// Method to be invoked by the networking library when 
        /// data is available
        /// </summary>
        /// <param name="state"></param>
        private void ReceiveMessage(SocketState state)
        {
            if (state.ErrorOccurred)
            {
                // inform the view
                Error?.Invoke("Lost connection to server");
                Debug.WriteLine("Error");
                return;
            }
            ProcessMessages(state);

            Debug.WriteLine("Message processed");

            // Continue the event loop
            // state.OnNetworkAction has not been changed, 
            // so this same method (ReceiveMessage) 
            // will be invoked when more data arrives
            Networking.GetData(state);
        }

        /// <summary>
        /// Process any buffered messages separated by '\n'
        /// Then inform the view
        /// </summary>
        /// <param name="state"></param>
        private void ProcessMessages(SocketState state)
        {
            string totalData = state.GetData();
            string[] parts = Regex.Split(totalData, @"(?<=[\n])");

            // Loop until we have processed all messages.
            // We may have received more than one.

            List<string> newMessages = new List<string>();

            foreach (string p in parts)
            {
                // Ignore empty strings added by the regex splitter
                if (p.Length == 0)
                    continue;
                // The regex splitter will include the last string even if it doesn't end with a '\n',
                // So we need to ignore it if this happens. 
                if (p[p.Length - 1] != '\n')
                    break;

                newMessages.Add(p);
                // Then remove it from the SocketState's growable buffer
                state.RemoveData(0, p.Length);
            }

            // lock the whole world
            lock (world)
            {

                // go through each string, find which type of object it is, deserialize and add to world
                foreach (string p in newMessages)
                {
                    JsonDocument doc = JsonDocument.Parse(p);

                    if (doc.RootElement.TryGetProperty("snake", out _))
                    {
                        Snake? snake = JsonSerializer.Deserialize<Snake>(p);
                        if (world.snakes.ContainsKey(snake!.snake))
                        {
                            world.snakes[snake!.snake] = snake;
                        }
                        else
                            world.snakes.Add(snake!.snake, snake);
                    }

                    if (doc.RootElement.TryGetProperty("wall", out _))
                    {
                        Wall? wal = JsonSerializer.Deserialize<Wall>(p);
                        if (world.walls.ContainsKey(wal!.wall))
                        {
                            world.walls[wal!.wall] = wal;
                        }
                        else
                            world.walls.Add(wal!.wall, wal);
                    }

                    if (doc.RootElement.TryGetProperty("power", out _))
                    {
                        Powerup? power = JsonSerializer.Deserialize<Powerup>(p);
                        if (world.powerups.ContainsKey(power!.power))
                        {
                            world.powerups[power!.power] = power;

                        }
                        else
                            world.powerups.Add(power!.power, power);

                        if (power.died == true)
                        {
                            world.powerups.Remove(power!.power);
                        }
                    }

                }
            }

            // onFrame after json has been deserialized
            MessagesArrived?.Invoke();

        }

        /// <summary>
        /// Closes the connection with the server
        /// </summary>
        public void Close()
        {
            theServer?.TheSocket.Close();
        }


        // send back to server
        public void Send(string s)
        {
            Networking.Send(theServer!.TheSocket, s);
        }

        // set our name, other classes will use this
        public void SetName(string name)
        {
            this.name = name;
        }

    }
}




