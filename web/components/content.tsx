import React, { useEffect, useState } from 'react';
import styles from '../styles/Content.module.css';
import { sendEvent } from '../services/serviceTopic';
import { useGetJsonTopicService } from '../services/topics';

export default function Content(props: any) {
    const [state, setState] = useState<string>("")
    const [error, setError] = useState<undefined | string>(undefined)
    const jsonExample = useGetJsonTopicService(props.topic);

    useEffect(() => setState(""), [props.topic]);

    function generateExample() {
        setState(JSON.stringify(jsonExample, null, 4))
    }

    function send() {
        try {
            sendEvent(props.topic, state);
        } catch (error: any) {
            setError(error)
        }
    }

    if (error) throw new Error(error);
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
