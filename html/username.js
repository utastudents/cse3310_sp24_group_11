const usernameInput = document.getElementById("username");
const usernameError = document.getElementById("usernameError");
const submitButton = document.getElementById("submitUsername");
const lobbyDiv = document.getElementById("lobby");

function isValidUsername(username) {
    if (!username.trim()) {
        return "Username cannot be blank.";
    }
    if (username.length > 8) {
        return "Username must be less than 8 characters.";
    }
    return ""; 
}

function submitForm(event) {
    event.preventDefault(); 

    const username = usernameInput.value;
    const errorMessage = isValidUsername(username);

    if (errorMessage) {
        usernameError.textContent = errorMessage;
    } else {
        
        document.getElementById("getUsername").style.display = "none";
        lobbyDiv.style.display = "block"; 
        
    }
}

submitButton.addEventListener("click", submitForm);
