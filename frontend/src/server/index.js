const WebSocket = require("ws");

const wss = new WebSocket.Server({port:8082});

//when the server starts up, do this
wss.on("connection", ws => {
    console.log("New client connected");

    const data = [
        {
            weather: "sunny",
            track: "sand",
            time: "night"
        },
        {
            horse: "horse1",
            weight: 100,
            color: "black"
        },
        {
            horse: "horse2",
            weight: 200,
            color: "white"
        }
    ]

    ws.send(JSON.stringify(data));

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

//when we receive messages, do these things
/*
wss.onmessage = function(event){
    if(typeOf(event.data) === String){
        //create a json object
        var jsonObject = JSON.parse(event.data);
        var name = jsonObject.name;
        var stat = jsonObject.stat;

        console.log("recieved data");
    }
}
*/