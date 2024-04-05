const WebSocket = require("ws");

const wss = new WebSocket.Server({port:8081});

const data = [
    {
        track: "Sand",
        weather: "Rain"
    },
    {
        name: "horse1",
        maneStyle: "mane1",
        race:"race1",
        injuries: 3,
        tailLength: 12
    },
    {
        name: "horse2",
        maneStyle: "mane2",
        race:"race2",
        injuries: 4,
        tailLength: 9
    },
    {
        name: "horse3",
        maneStyle: "mane3",
        race:"race3",
        injuries: 2,
        tailLength: 10
    },
    {
        name: "horse4",
        maneStyle: "mane4",
        race:"race4",
        injuries: 1,
        tailLength: 12
    },
    {
        name: "horse5",
        maneStyle: "mane5",
        race:"race5",
        injuries: 1,
        tailLength: 10
    },
    {
        name: "horse6",
        maneStyle: "mane6",
        race:"race6",
        injuries: 2,
        tailLength: 8
    },
    {
        name: "horse7",
        maneStyle: "mane7",
        race:"race7",
        injuries: 0,
        tailLength: 13
    },
    {
        name: "horse8",
        maneStyle: "mane8",
        race:"race8",
        injuries: 2,
        tailLength: 8
    }
    
]

const data2 = [
    {
        track: "Sand",
        weather: "Rain"
    },
    {
        winner: "horse6",
        maneStyle: "mane6",
        race: "race6",
        injuries: 2,
        tailLength: 8
    }
]

const data3 = [
    {
        dateTime: "12:15 4/1/24",
        track: "grass",
        weather: "sun",
        winner: "horse2"
    },
    {
        dateTime: "12:30 4/1/24",
        track: "lava",
        weather: "snow",
        winner: "horse4"
    }
]


//when the server starts up, do this
wss.on("connection", ws => {
    console.log("New client connected");

    ws.send(JSON.stringify(data2));
    ws.send(JSON.stringify(data));
    ws.send(JSON.stringify(data3));

    //receives a message from the browser and prints it in the console
    //sends a confirmation message back
    ws.on("message", function incoming(message){
        //without the .toString() method we just get a regular buffer object, which we cant read
        //JSON.parse() does not need .toString()
        //console.log(JSON.parse(message));

        //this creates a JSON object out of the message and puts its variables on the console
        const data = JSON.parse(message);
        console.log(data.x, data.y);
        //sending a message to the client
        //ws.send("received: " + data.x +" " + data.y);
    });

    ws.on("close", ws => {
        console.log("Client disconnected");
    });
});