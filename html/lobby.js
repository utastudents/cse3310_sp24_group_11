function showPlayers(){
    document.getElementById("Lobby").style.display = "none";
    document.getElementById("Players").style.display = "table";
}
function showLeaderboard(){
    document.getElementById("Lobby").style.display = "none";
    document.getElementById("Leaderboard").style.display = "table";
}
function GlobalChat(){
    document.getElementById("Lobby").style.display = "none";
    document.getElementById("GlobalChat").style.display = "table";
}
function initialLobby(){

}
function backToLobby(){
    console.log("Back to lobby function called");
    document.getElementById("Players").style.display = "none";
    document.getElementById("content").style.display = "none";
    document.getElementById("Leaderboard").style.display = "none";
    document.getElementById("GlobalChat").style.display = "none";
    document.getElementsByClassName("chatting")[0].style.display = "none";
    document.getElementById("Game").style.display = "none";
    document.getElementById("twoPlayerGame").style.display = "none";
    document.getElementById("threePlayerGame").style.display = "none";
    document.getElementById("fourPlayerGame").style.display = "none";
    document.getElementById("Lobby").style.display = "table";
}
function showButPlay(){
    //add username to play
    let play = username.value + "<br>";
    for(let i = 0; i < 4; i++){
        play += "Player " + i + "<br>";
    }
    document.getElementById("content").innerHTML = play;
    document.getElementById("content").style.display = "table";
}

function leaderboard(){
    //add leaderboard
    let lead = "Leaderboard<br>";
    lead += username.value + "<br>";
    for(let i = 0; i < 4; i++){
        lead += "Player " + i + "<br>";
    }
    document.getElementById("content").innerHTML = lead;
    document.getElementById("content").style.display = "table";
}
function chat(){
    document.getElementById("GlobalChat").style.display = "none";
    document.getElementsByClassName("chatting")[0].style.display = "block";
}

const chatBox = document.getElementById("chatBox");
const messageForm = document.getElementById("messageForm");
const messageInput = document.getElementById("messageInput");
messageForm.addEventListener("submit", actuallyChatting);
function actuallyChatting(){
    event.preventDefault();
    const message = messageInput.value;
    // if(message === ""){
    //     alert('Message cannot be left blank!');
    //     return;
    // }
    messageInput.value = "";
    chatBox.innerHTML += username.value + ": " + message + "<br>";
}

function twoPlayerGame() {
    // Hide all elements except the "Two Player Game" section
    document.getElementById("Players").style.display = "none";
    document.getElementById("content").style.display = "none";
    document.getElementById("Leaderboard").style.display = "none";
    document.getElementById("GlobalChat").style.display = "none";
    document.getElementById("Game").style.display = "none";
    document.getElementById("Lobby").style.display = "none";
    
    // Show the "Two Player Game" section
    document.getElementById("twoPlayerGame").style.display = "block";
    
    // Reset player readiness
    player1Ready = false;
    player2Ready = false;
    
    // Reset ready buttons
    document.getElementById('ready1').innerText = 'Ready';
    document.getElementById('ready2').innerText = 'Ready';
    
    // Add event listeners for ready buttons
    document.getElementById('ready1').addEventListener('click', function() {
        toggleReady(1);
    });
    
    document.getElementById('ready2').addEventListener('click', function() {
        toggleReady(2);
    });
    
    // Clear any messages
    document.getElementById('message').innerText = '';
}

function threePlayerGame() {
    
    document.getElementById("Players").style.display = "none";
    document.getElementById("content").style.display = "none";
    document.getElementById("Leaderboard").style.display = "none";
    document.getElementById("GlobalChat").style.display = "none";
    document.getElementById("Game").style.display = "none";
    document.getElementById("Lobby").style.display = "none";
    
    // Show the "Two Player Game" section
    document.getElementById("threePlayerGame").style.display = "block";
    
    // Reset player readiness
    player1Ready = false;
    player2Ready = false;
    player3Ready = false;
    
    // Reset ready buttons
    document.getElementById('ready1').innerText = 'Ready';
    document.getElementById('ready2').innerText = 'Ready';
    document.getElementById('ready3').innerText = 'Ready';

    // Add event listeners for ready buttons
    document.getElementById('ready1').addEventListener('click', function() {
        toggleReady(1);
    });
    document.getElementById('ready2').addEventListener('click', function() {
        toggleReady(2);
    });
    document.getElementById('ready3').addEventListener('click', function() {
        toggleReady(3);
    });
    
    // Clear any messages
    document.getElementById('message').innerText = '';
}

function fourPlayerGame() {
    
    document.getElementById("Players").style.display = "none";
    document.getElementById("content").style.display = "none";
    document.getElementById("Leaderboard").style.display = "none";
    document.getElementById("GlobalChat").style.display = "none";
    document.getElementById("Game").style.display = "none";
    document.getElementById("Lobby").style.display = "none";
    
    // Show the "Two Player Game" section
    document.getElementById("fourPlayerGame").style.display = "block";
    
    // Reset player readiness
    player1Ready = false;
    player2Ready = false;
    player3Ready = false;
    player4Ready = false;
    
    // Reset ready buttons
    document.getElementById('ready1').innerText = 'Ready';
    document.getElementById('ready2').innerText = 'Ready';
    document.getElementById('ready3').innerText = 'Ready';
    document.getElementById('ready4').innerText = 'Ready';

    // Add event listeners for ready buttons
    document.getElementById('ready1').addEventListener('click', function() {
        toggleReady(1);
    });
    document.getElementById('ready2').addEventListener('click', function() {
        toggleReady(2);
    });
    document.getElementById('ready3').addEventListener('click', function() {
        toggleReady(3);
    });
    document.getElementById('ready4').addEventListener('click', function() {
        toggleReady(4);
    });
    
    // Clear any messages
    document.getElementById('message').innerText = '';
}

function toggleReady(playerNumber) {
    if (playerNumber === 1) {
        player1Ready = !player1Ready;
        document.getElementById('ready1').innerText = player1Ready ? 'Unready' : 'Ready';
    } else if (playerNumber === 2) {
        player2Ready = !player2Ready;
        document.getElementById('ready2').innerText = player2Ready ? 'Unready' : 'Ready';
    }

    checkReadyStatus();
}

function checkReadyStatus() {
    if (player1Ready && player2Ready) {
        document.getElementById('message').innerText = 'Both players are ready! Starting the game...';
        // add code to start game HERE
    } else {
        document.getElementById('message').innerText = '';
    }
}

