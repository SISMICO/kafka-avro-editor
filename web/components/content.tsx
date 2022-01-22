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

    return (
        <>
            <div className={styles.panel}>
                <h2>Topic: {props.topic}</h2>
                <button onClick={generateExample}>Generate Example</button>
                <input type="text" value={state}></input>
                <textarea
                    rows={20}
                    cols={100}
                    onChange={e => setState(e.target.value)}
                    value={state}>
                </textarea>
            </div>
        </>
    )
}
