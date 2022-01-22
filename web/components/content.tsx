import React, { useEffect, useState } from 'react';
import useSWR from 'swr';
import styles from '../styles/Content.module.css';
import getTopics from '../services/serviceTopic';

//const fetcher = (url: RequestInfo) => fetch(url).then((res) => JSON.stringify(res.json(), null, 4))
const fetcher = (url: RequestInfo) => fetch(url).then((res) => res.json())

export default function Content(props: any) {
    const [state, setState] = useState("")

    function generateExample() {
        //const { data, error } = useSWR<string, Error>(`http://localhost:8080/json/${props.topic}`, fetcher)
        getTopics(props.topic)
            .then((data) =>
                setState(JSON.stringify(data, null, 4))
            );

        //if (error) return <div>Failed to load</div>
        //if (!data) return <div>Loading...</div>
    }

    function sendEvent() {
        console.log("Send Event");
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
                    <button onClick={sendEvent}>Send Event</button>
                </div>
            </div>
        </>
    )
}
