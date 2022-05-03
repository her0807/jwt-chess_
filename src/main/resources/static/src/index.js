const start = document.querySelector("#start")
const score = document.querySelector("#status")
const back = document.querySelector("#back")
const end = document.querySelector("#end")
const id = new URL(window.location).searchParams.get('id')


let from = ""
let to = ""
let status = ""

window.onload = load

start.addEventListener('click', async function () {
    startAndDraw()
})

back.addEventListener('click', async function () {
    window.location.replace("/");
})

async function load() {
    await fetch("/rooms/game/" + id)
        .then(res => res.json())
        .then(res => {
            if (res.gameStatus == "PLAYING") {
                status = res.gameStatus;
                drawBoard(res.board)
            }
        })
}

async function startAndDraw() {
    await fetch("/rooms/game/start/" + id)
        .then(res => res.json())
        .then(res => {
            status = res.gameStatus;
            drawBoard(res.board)
        })
}

function drawBoard(res) {
    Object.keys(res).forEach((e) => {
        let eachDiv = document.querySelector("#" + e)
        putPiece(eachDiv, res, e)
    })
}

function putPiece(eachDiv, board, value) {
    if (eachDiv.hasChildNodes()) {
        eachDiv.removeChild(eachDiv.firstChild)
    }
    if (board[value] != "blank") {
        const img = document.createElement("img")
        img.classList.add('img-style')
        img.src = "/images/piece/" + board[value] + ".png"
        eachDiv.appendChild(img)
    }
}

function tryMove(e) {
    if (from === "") {
        from = e
        document.getElementById(from).style.backgroundColor = 'white'
        return;
    }

    if (from !== "" && to === "") {
        to = e
        document.getElementById(from).style.backgroundColor = ''
        movePiece(from, to)
        from = ""
        to = ""
    }
}

async function movePiece(from, to) {
    const bodyValue = {
        from: from,
        to: to,
        roomId: id
    }

    fetch("/rooms/game/move", {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(bodyValue)
    }).then(res => res.json())
        .then(res => {
            if (res.board == undefined) {
                throw new Error(res.message)
            }
            if (res.gameStatus == "CHECK_MATE") {
                status = res.gameStatus
                return endGame();
            } else {
                status = res.gameStatus
                drawAfterBoard(res.board)
            }
        }).catch(reason => alert(reason))

    function drawAfterBoard(res) {
        Object.keys(res).forEach((e) => {
            let eachDiv = document.querySelector("#" + e)
            putPiece(eachDiv, res, e)
        })
        removeFromPiece();
    }

    function removeFromPiece() {
        let div = document.getElementById(from);
        if (div.hasChildNodes()) {
            div.removeChild(div.firstChild)
        }
    }

    if (status == "END" || status == "CHECK_MATE") {
        await endGame();
    }
}

score.addEventListener('click', async function () {
    await getScore()
})

async function getScore() {
    if (status === "") {
        alert("게임을 시작해야합니다.");
    } else {
        let score = await fetch("/rooms/game/status/" + id)
        score = await score.json()
        alert(score.message)
    }
}

end.addEventListener('click', async function () {
    endGame()
})

async function endGame() {
    if (status === "") {
        alert("게임을 시작해야합니다.");
    } else {
        let score = await fetch("/rooms/game/end/" + id,
        {
            method: 'POST'
        })
        score = await score.json()
        if (status === "PLAYING") {
            alert("게임을 종료합니다.\n" + score.message);
        }
        if (status === "CHECK_MATE") {
            alert("승부가 결정되었습니다!!.\n" + score.message);
        }

        status = "";
        window.location.reload();
    }
}

