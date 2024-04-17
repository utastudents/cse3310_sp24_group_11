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
function backToLobby(){
    document.getElementById("Players").style.display = "none";
    document.getElementById("content").style.display = "none";
    document.getElementById("Leaderboard").style.display = "none";
    document.getElementById("GlobalChat").style.display = "none";
    document.getElementsByClassName("chatting")[0].style.display = "none";
    document.getElementById("Game").style.display = "none";
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
