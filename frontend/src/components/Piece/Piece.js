import React from 'react';
import styles from './Piece.module.css';

function Piece({ color, size }) {
    const pieceClass = styles.piece +
    ' ' +
    styles[color] +
    ' ' +
    styles[size];

    return (
        <div className={pieceClass}></div>
    );
}

export default Piece;