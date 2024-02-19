const WebSocket = require("ws");

const wss = new WebSocket.Server({port:8082});

//when the server starts up, do this
wss.on("connection", ws => {
    console.log("New client connected");

    ws.on("close", ws => {
        console.log("Client disconnected");
    });
});

//when we receive messages, do these things
wss.onmessage = function(event){
    if(typeOf event.data === String){
        //create a json object
        var jsonObject = JSON.parse(event.data);
        var name = jsonObject.name;
        var stat = jsonObject.stat;

        console.log("recieved data");
    }
}