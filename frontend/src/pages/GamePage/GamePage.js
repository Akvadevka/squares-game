import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import styles from "./GamePage.module.css";
import Piece from "../../components/Piece/Piece";

function GamePage() {
    const location = useLocation();
    const { size = 6, userColor = "b", firstPlayer = "user" } = location.state || {};

    const [currentTurn, setCurrentTurn] = useState(firstPlayer);

    const [board, setBoard] = useState(
        Array.from({ length: size }, () => Array.from({ length: size }, () => null))
    );

    const handleCellClick = (rowIndex, colIndex) => {
        console.log(currentTurn);
        setBoard(prev => {
            const newBoard = prev.map(r => [...r]);
            if (!newBoard[rowIndex][colIndex]) {
                newBoard[rowIndex][colIndex] = currentTurn === "user" ? userColor : (userColor === "b" ? "w" : "b");
            }

            return newBoard;
        });
        setCurrentTurn(prevTurn => (prevTurn === "user" ? "comp" : "user"));
        console.log(currentTurn);
    };

    return (
        <div className={styles.wrapper}>
            <div className={styles.playersPanel}>
                <div className={`${styles.playerCard} ${currentTurn === "user" ? styles.active : styles.disabled}`}>
                    <div className={styles.container}>
                        <Piece color={userColor} size="small"/>
                        <div>
                            <h2 className="semiBold">Игрок</h2>
                            <h4 className="semiBold gr-clr">{userColor === "b" ? "Black" : "White"}</h4>
                        </div>
                    </div>
                    {currentTurn === "user" && <div className={styles.greenDot}></div>}
                </div>

                <div className={`${styles.playerCard} ${currentTurn === "comp" ? styles.active : styles.disabled}`}>
                    <div className={styles.container}>
                        <Piece color={userColor === "b" ? "w" : "b"} size="small"/>
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
