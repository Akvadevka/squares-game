// frontend/src/pages/GameSetup/GameSetup.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // <--- Импортируем useNavigate
import styles from './GameSetup.module.css';
import Piece from '../../components/Piece/Piece';

const MIN_SIZE = 3;

// Пропсы onStartGame больше не нужен
function GameSetup() {
    const [size, setSize] = useState(3);
    const [userColor, setUserColor] = useState('b');
    const [firstPlayer, setFirstPlayer] = useState('user');

    const navigate = useNavigate();

    const getPlayerButtonClass = (playerType) => {
        let className = styles.player_button;

        if (firstPlayer === playerType) {
            className += ' ' + styles.player_button_active;
        }
        return className;
    };
    const handleStart = () => {
        if (size >= MIN_SIZE) {
            // Используем navigate для перехода и передачи объекта state
            navigate('/game', {
                state: {
                    size,
                    userColor,
                    firstPlayer
                }
            });
        } else {
            alert(`Размер поля должен быть минимум ${MIN_SIZE}`);
        }
    };


    return (
        <div className={styles.wrapper}>
            <div className={styles.settings_title}>
                <h1 className="bold">Запуск игры</h1>
                <h4 className="medium gr-clr">Перед началом игры выберите параметры</h4>
            </div>
            <div className={styles.settings_size}>
                <h4 className="medium gr-clr">Размер поля</h4>
                <input
                    id="sizeInput"
                    type="number"
                    min={MIN_SIZE}
                    value={size}
                    onChange={(e) => setSize(e.target.value)}
                    className={styles.input_field}
                />
                <h4 className="medium gr-clr">Минимум {MIN_SIZE}</h4>
            </div>
            <div className={styles.settings_color}>
                <h3 className="medium">Выберите свой цвет</h3>
                <div className={styles.color_options}>

                    <div
                        className={`${styles.color_option} ${userColor === 'b' ? styles.color_active : ''}`}
                        onClick={() => setUserColor('b')}
                    >
                        <Piece color="b" size="medium" />
                    </div>

                    <div
                        className={`${styles.color_option} ${userColor === 'w' ? styles.color_active : ''}`}
                        onClick={() => setUserColor('w')}
                    >
                        <Piece color="w" size="medium" />
                    </div>
                </div>
            </div>
            <div className={styles.settings_first_run}>
                <h3 className="medium">Кто ходит первый</h3>
                <div className={styles.player_options_container}>
                    <button
                        className={getPlayerButtonClass('user')}
                        onClick={() => setFirstPlayer('user')}
                    >
                        <h3 className="semiBold">Игрок</h3>
                    </button>

                    <button
                        className={getPlayerButtonClass('comp')}
                        onClick={() => setFirstPlayer('comp')}
                    >
                        <h3 className="semiBold">Компьютер</h3>
                    </button>
                </div>
                <button
                    className={styles.startButtonClass}
                    onClick={handleStart}
                >
                    <h2 className="medium">Начать игру</h2>
                </button>
            </div>

        </div>
    );
}

export default GameSetup;