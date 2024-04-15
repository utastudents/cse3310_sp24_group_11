document.addEventListener('DOMContentLoaded', function() {
    generateGrid();
});

function generateGrid() {
    const grid = document.getElementById('wordSearchGrid');
    const size = 50;
    for (i = 0; i < size; i++) {
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
