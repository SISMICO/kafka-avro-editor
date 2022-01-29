import { BaseSyntheticEvent } from 'react';
import React, { useState } from 'react';
import useSWR from 'swr'
import styles from '../styles/Topics.module.css'
import Content from '../components/content'

const fetcher = (url: RequestInfo) => fetch(url).then((res) => res.json());

export default function Topics() {
    const { data, error } = useSWR<string[], Error>(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/topics`, fetcher);
    const [state, setstate] = useState({ selectedTopic: "" })

    if (error) return <div>Failed to load</div>
    if (!data) return <div>Loading...</div>

    const handleClick = (e: BaseSyntheticEvent, topic: string) => {
        setstate({ selectedTopic: topic })
        console.log(topic);
    };

    return (
        <>
            <div className={styles.group}>
                <div className={styles.topics_panel}>
                    <h1>Topics</h1>
                    <ul>
                        {data.map((topic) => (
                            <a href='#' key={topic} onClick={(e) => handleClick(e, topic)}>
                                <li key={topic}>{topic}</li>
                            </a>
                        ))}
                    </ul>
                </div>
                {state.selectedTopic != "" &&
                    <Content topic={state.selectedTopic}></Content>
                }
            </div>
        </>
    )
}
