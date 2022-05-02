const start = document.getElementById('start-button');
const status = document.getElementById('status-button');
const IMAGE_PATH = ".././images/";
const BOARD = document.querySelector("#board");
const CURRENT_TEAM = document.querySelector("#current-team");
const SYMBOL_TO_IMAGE_PATH = {
    "p": IMAGE_PATH + "whitePawn.png",
    "P": IMAGE_PATH + "blackPawn.png",
    "r": IMAGE_PATH + "whiteRook.png",
    "R": IMAGE_PATH + "blackRook.png",
    "n": IMAGE_PATH + "whiteKnight.png",
    "N": IMAGE_PATH + "blackKnight.png",
    "b": IMAGE_PATH + "whiteBishop.png",
    "B": IMAGE_PATH + "blackBishop.png",
    "q": IMAGE_PATH + "whiteQueen.png",
    "Q": IMAGE_PATH + "blackQueen.png",
    "k": IMAGE_PATH + "whiteKing.png",
    "K": IMAGE_PATH + "blackKing.png"
};

let boardInfo = "";
let isChoiced = false;
let currentTurn = "";
let gameId = "";

function showStatusButton(id) {
    gameId = id;
    status.style.visibility = 'visible';
}

function initBoard() {
    fetch('/api/' + gameId + '/restart')
        .then(res => res.json())
        .then(imageSetting)
}

start.addEventListener('click', function () {
    if (start.textContent === "START") {
        loadBoard();
        move();
        start.textContent = "RESTART";
        return
    }
    window.alert("성공적으로 게임이 재시작되었습니다!")
    initBoard();
})

function getStatus(scoreResponse) {
    const blackScore = scoreResponse["blackTeamScore"];
    const whiteScore = scoreResponse["whiteTeamScore"];

    window.alert("블랙팀 점수 :" + blackScore + ", 하얀팀 점수 : " + whiteScore);
}

status.addEventListener('click', function () {
    fetch('/api/chess/' + gameId + '/status')
        .then(res => res.json())
        .then(getStatus)
})

function loadBoard() {
    fetch('/api/chess/' + gameId)
        .then(res => res.json())
        .then(imageSetting)
}

function imageSetting(response) {
    const divs = BOARD.querySelectorAll("div");
    boardInfo = response;
    pieces = response["board"];
    for (const div of divs) {
        const key = div.getAttribute("id");
        if (SYMBOL_TO_IMAGE_PATH[pieces[key]] !== undefined) {
            div.style.backgroundImage = "url(" + SYMBOL_TO_IMAGE_PATH[pieces[key]] + ")";
        } else {
            div.style.backgroundImage = null;
        }
    }
    turnSetting(response)
}

function turnSetting(response) {
    if (response["finish"] === true) {
        document.querySelector("#view-type").textContent = "승리자 :ㅤ";
        return;
    } else {
        document.querySelector("#view-type").textContent = "현재 턴 :ㅤ"
    }

    if (response["turn"] === "white") {
        currentTurn = "하얀색"
    } else {
        currentTurn = "검정색"
    }

    CURRENT_TEAM.textContent = currentTurn
}

let toTarget = "";
let fromTarget = "";

function initPosition() {
    fromTarget = "";
    toTarget = "";
}

function eventMove(event) {
    const turn = boardInfo["turn"];
    if (boardInfo["finish"] === true) {
        window.alert("게임이 종료되었습니다. RESTART 또는 STATUS만 눌러주세요.");
        return;
    }
    if (isChoiced === false) {
        fromTarget = event.target;
        const position = fromTarget.id

        if (pieces[position] === ".") {
            window.alert("기물을 선택하세요!");
            return
        }
        if (turn === "white" && pieces[position] !== pieces[position].toLowerCase() ||
            turn === "black" && pieces[position] !== pieces[position].toUpperCase()) {
            window.alert("자신의 기물을 선택하세요!");
            return;
        }
        event.target.style.backgroundColor = "skyblue";
        isChoiced = true;
        return;
    }
    if (isChoiced === true) {
        toTarget = event.target
        isChoiced = false;
    }
    if (toTarget.id !== "") {
        fromTarget.style.backgroundColor = "";
        if (fromTarget.id === toTarget.id) {
            initPosition();
            return;
        }
        movePiece(fromTarget.id, toTarget.id)
    }
}

function movePiece(from, to) {
    const request = {
        from: from,
        to: to
    }

    fetch('/api/chess/' + gameId + '/move', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    }).then(res => res.json())
        .then(res => imageSetting(res));
}

function move() {
    const divs = BOARD.querySelectorAll("div");

    for (const div of divs) {
        div.addEventListener("click", eventMove);
    }
}

function enterRoom(id) {
    var password = prompt('패스워드를 입력하세요');
    const request = {
        password: password
    }
    fetch('/api/chess/' + id + '/join', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    })
        .then(res => {
            if (!res.ok) {
                throw res
            }
            window.location.href = "/room/" + id
            return;
        }).catch(error => {
        error.text().then(msg => alert(msg))
    })
}

function deleteRoom(id) {
    var password = prompt('패스워드를 입력하세요');
    const request = {
        password: password
    }
    fetch('/api/chess/' + id, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    }).then(res => {
        if (!res.ok) {
            throw res
        }
    }).catch(error => {
        error.text().then(msg => alert(msg))
    })
}

function goHome() {
    window.location.href = "/";
}

function createRoom() {
    let title = document.getElementById("title").value;
    let password = document.getElementById("password").value;

    const request = {
        title: title,
        password: password
    };

    fetch('/api/chess/new', {
        method: 'post',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request)
    }).then((response) => {
        if (response.status === 400) {
            window.alert("제목이 중복되었습니다.")
            return;
        }
        window.alert("방 생성에 성공했습니다.");
        window.location.href = response.headers.get("Location");
    });
}
