import React, { useState, useEffect, useRef  } from "react";
import { useLocation } from "react-router-dom";
import styles from "./GamePage.module.css";
import Piece from "../../components/Piece/Piece";

function GamePage() {
    const location = useLocation();
    const { size = 6, userColor = "b", firstPlayer = "user" } = location.state || {};
    const [gameStatus, setGameStatus] = useState(null);
    const [currentTurn, setCurrentTurn] = useState(() => {
        return localStorage.getItem("currentTurn") || firstPlayer;
    });

    const [board, setBoard] = useState(() => {
        const saved = localStorage.getItem("gameBoard");
        if (saved) {
            return JSON.parse(saved);
        }
        return Array.from({ length: size }, () =>
            Array.from({ length: size }, () => null)
        );
    });

    useEffect(() => {
        localStorage.setItem("gameBoard", JSON.stringify(board));
    }, [board]);

    useEffect(() => {
        localStorage.setItem("currentTurn", currentTurn);
    }, [currentTurn]);

    const boardToString = (board) => {
        return board.flat().map(cell => cell || " ").join("");
    };

    const gameStatusRef = useRef(gameStatus);

    useEffect(() => {
        gameStatusRef.current = gameStatus;
    }, [gameStatus]);


    useEffect(() => {
        if (currentTurn !== "comp") return;

        const makeCompMove = async () => {

            await new Promise(resolve => setTimeout(resolve, 700));
            if (gameStatusRef.current) return;

            const data = {
                size,
                data: boardToString(board),
                nextPlayerColor: userColor === "b" ? "w" : "b"
            };

            try {
                const response = await fetch("http://localhost:8080/api/squares/nextMove", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(data)
                });

                const result = await response.json();

                setBoard(prev => {
                    const newBoard = prev.map(r => [...r]);
                    newBoard[result.y][result.x] = result.color;
                    return newBoard;
                });

                setCurrentTurn("user");
            } catch (error) {
                console.error("Ошибка при ходе компьютера:", error);
            }
        };

        makeCompMove();
    }, [currentTurn]);

    const checkGameStatus  = async (nextPlayer) => {
        const data = {
            size,
            data: boardToString(board),
            nextPlayerColor: nextPlayer === "user" ? userColor : (userColor === "b" ? "w" : "b")
        };

        try {
            const response = await fetch("http://localhost:8080/api/squares/gameStatus", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });
            const result = await response.json();

            if (result.status !== 0) {
                setGameStatus(result);
            }
        } catch (error) {
            console.error("Ошибка при запросе gameStatus:", error);
        }
    };

    const handleCellClick = (rowIndex, colIndex) => {
        if (currentTurn !== "user") return;

        setBoard(prev => {
            const newBoard = prev.map(r => [...r]);
            if (!newBoard[rowIndex][colIndex]) {
                newBoard[rowIndex][colIndex] = userColor;
                setCurrentTurn("comp");
            }
            return newBoard;
        });
    };

    useEffect(() => {
        if (!board) return;
        checkGameStatus(currentTurn);
    }, [board, currentTurn]);

    const renderPopupMessage = () => {
        if (!gameStatus) return null;

        if (gameStatus.status === 2) {
            return "Ничья!";
        }
        if (gameStatus.status === 1) {
            if (gameStatus.color === userColor) {
                return `Игрок (${gameStatus.color}) выиграл! 🎉`;
            } else {
                return `Компьютер (${gameStatus.color}) выиграл! 🤖`;
            }
        }
        if (gameStatus.status === -1) {
            return `Ошибка: ${gameStatus.message}`;
        }
        return null;
    };

    return (
        <div className={styles.wrapper}>
            {gameStatus && (
                <div className={styles.popup}>
                    <div className={styles.popupContent}>
                        <h2 className="semiBold">{renderPopupMessage()}</h2>
                        <button className={styles.startButtonClass} onClick={() => window.location.href = "/"}>
                            Играть снова
                        </button>
                    </div>
                </div>
            )}
            <div className={styles.playersPanel}>
                <div className={`${styles.playerCard} ${currentTurn === "user" ? styles.active : styles.disabled}`}>
                    <div className={styles.container}>
                        <Piece color={userColor} size="medium"/>
                        <div>
                            <h2 className="semiBold">Игрок</h2>
                            <h4 className="semiBold gr-clr">{userColor === "b" ? "Black" : "White"}</h4>
                        </div>
                    </div>
                    {currentTurn === "user" && <div className={styles.greenDot}></div>}
                </div>

                <div className={`${styles.playerCard} ${currentTurn === "comp" ? styles.active : styles.disabled}`}>
                    <div className={styles.container}>
                        <Piece color={userColor === "b" ? "w" : "b"} size="medium"/>
                        <div>
                            <h2 className="semiBold">Компьютер</h2>
                            <h4 className="semiBold gr-clr">{userColor === "b" ? "White" : "Black"}</h4>
                        </div>
                    </div>
                    {currentTurn === "comp" && <div className={styles.greenDot}></div>}
                </div>
            </div>

            <div
                className={styles.board}
                style={{
                    gridTemplateColumns: `repeat(${size}, 1fr)`,
                    gridTemplateRows: `repeat(${size}, 1fr)`,
                }}
            >
                {board.map((row, rowIndex) =>
                    row.map((cell, colIndex) => (
                        <div key={`${rowIndex}-${colIndex}`}
                             className={styles.cell}
                             onClick={() => handleCellClick(rowIndex, colIndex)}
                        >
                            {cell === "b" && <Piece color="b" size="small"/>}
                            {cell === "w" && <Piece color="w" size="small"/>}
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

export default GamePage;
