var connection = new signalR.HubConnectionBuilder().withUrl("/syncHub").build();

connection.on("ReceiveMessage", function () {
    loadCurrentPeriod();
});

connection.start().then(function () {
    
}).catch(function (err) {
    return console.error(err.toString());
});

