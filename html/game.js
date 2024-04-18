function game(){
    var serverUrl;
    serverUrl = "ws://" + window.location.hostname +":"+ (parseInt(location.port) + 100);
    connection = new WebSocket(serverUrl);
    idx = -1;
    var gameid = -1;
    connection.onopen = function(e){
        console.log("Connected to server");
        connection.send(JSON.stringify({name: username.value}));
    }
    connection.onclose = function(e){
        console.log("Disconnected from server");
    }
    connection.onmessage = function(e){
        const obj = JSON.parse(e.data);
        document.write(obj);
    }
}

function generateGrid() {
    const grid = document.getElementById('wordSearchGrid');
    const size = 50;
    for (let i = 0; i < size; i++) {
        const row = document.createElement('tr');
        for (let j = 0; j < size; j++) {
            const cell = document.createElement('td');
            cell.innerText = String.fromCharCode('A'.charCodeAt(0) + Math.floor(Math.random() * 26));
            row.appendChild(cell);
        }
        grid.appendChild(row);
    }
}

function sendMessage() {
    const input = document.getElementById('messageInput');
    if (input.value.trim() !== '') {
        const messages = document.getElementById('messages');
        const message = document.createElement('div');
        message.innerText = input.value;
        messages.appendChild(message);
        input.value = ''; 
        messages.scrollTop = messages.scrollHeight;
    }
}

function setupGridEvents() {
    const cells = document.querySelectorAll('#wordSearchContainer td');
    let isDragging = false;

    cells.forEach(cell => {
        cell.addEventListener('mousedown', (event) => {
            event.preventDefault();
            isDragging = true;
            toggleHighlight(cell);
        });

        cell.addEventListener('mouseover', (event) => {
            if (isDragging) {
                event.preventDefault();
                toggleHighlight(cell);
            }
        });
    });

    document.addEventListener('mouseup', () => {
        isDragging = false;
    });
}

function toggleHighlight(cell) {
    cell.classList.toggle('highlighted');
}
