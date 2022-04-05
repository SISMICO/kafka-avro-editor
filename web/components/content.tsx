import React, { useEffect, useState } from 'react';
import styles from '../styles/Content.module.css';
import { sendEvent, getJsonTopicService } from '../services/topics';

export default function Content(props: any) {
    const [state, setState] = useState<string>("")
    const [error, setError] = useState<undefined | Error>(undefined)


    useEffect(() => setState(""), [props.topic]);

    function generateExample() {
        getJsonTopicService(props.topic)
            .then(jsonExample => setState(JSON.stringify(jsonExample, null, 4)))
            .catch(error => setError(error))
    }

    function send() {
        sendEvent(props.topic, state)
            .then(result => {
                if (result.error)
                    setError(result.error)
            });
    }

    if (error) throw error;
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
