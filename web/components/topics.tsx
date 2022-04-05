import { BaseSyntheticEvent } from 'react';
import React, { useState } from 'react';
import styles from '../styles/Topics.module.css'
import Content from '../components/content'
import ListTopics from './list-topics'
import { ErrorBoundary } from './error-boundary';

export default function Topics() {
    const [state, setState] = useState({ selectedTopic: '' });
    const [filter, setFilter] = useState('');

    const handleClick = (e: BaseSyntheticEvent, topic: string) => {
        setState({ selectedTopic: topic })
    };

    const topicFilter = (event: React.ChangeEvent<HTMLInputElement>) => {
        let value = event.target.value;
        setFilter(value)
    };

    return (
        <>
            <ErrorBoundary>
                <div className={styles.group}>
                    <div className={styles.topics_panel}>
                        <h1>Topics</h1>
                        <div>
                            <input type="text" onChange={topicFilter}></input>
                        </div>
                        <ListTopics filter={filter} handleClick={handleClick}></ListTopics>
                    </div>
                    {state.selectedTopic != "" &&
                        <Content topic={state.selectedTopic}></Content>
                    }
                </div>
            </ErrorBoundary>
        </>
    )
}
