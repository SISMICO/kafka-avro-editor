import React, { useEffect, useState } from 'react';
import useSWR from 'swr';
import styles from '../styles/Content.module.css';
import { getTopics, sendEvent } from '../services/serviceTopic';

const fetcher = (url: RequestInfo) => fetch(url).then((res) => res.json())

export default function Content(props: any) {
    const [state, setState] = useState("")

    function generateExample() {
        getTopics(props.topic)
            .then((data: any) =>
                setState(JSON.stringify(data, null, 4))
            );
    }

    function send() {
        sendEvent(props.topic, state);
    }

    return (
        <>
            <div className={styles.message_panel}>
                <h1>Selected Topic: <span>{props.topic}</span></h1>
                <div>
                    <button onClick={generateExample}>Generate Example</button>
                </div>
                <div>
                    <textarea
                        onChange={e => setState(e.target.value)}
                        value={state}>
                    </textarea>
                </div>
                <div>
                    <button onClick={send}>Send Event</button>
                </div>
            </div>
        </>
    )
}
