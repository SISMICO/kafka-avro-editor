import { BaseSyntheticEvent } from 'react';
import React, { useState } from 'react';
import styles from '../styles/Topics.module.css'
import Content from '../components/content'
import ListTopics from '../components/listTopics'

export default function Topics() {
    const [state, setstate] = useState({ selectedTopic: '' });
    const [filter, setFilter] = useState('');

    const handleClick = (e: BaseSyntheticEvent, topic: string) => {
        setstate({ selectedTopic: topic })
        console.log(topic);
    };

    const topicFilter = (event: React.ChangeEvent<HTMLInputElement>) => {
        let value = event.target.value;
        setFilter(value)
        console.log(value);
    };

    return (
        <>
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
        </>
    )
}
