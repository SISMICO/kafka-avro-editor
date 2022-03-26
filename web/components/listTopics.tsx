import React, { useEffect, useState } from 'react';
import { getTopics } from '../api/topics'

interface ListTopicsProps {
    filter: string,
    handleClick: (event: any, topic: string) => void
}

export default function ListTopics(props: React.PropsWithChildren<ListTopicsProps>) {
    const [topics, setTopics] = useState([]);

    useEffect(() => {
        getTopics(props.filter).then(result => 
            setTopics(result)
        );
    }, [props.filter])

    return (
        <>
            <ul>
                {topics.map(
                    (topic: string) => (
                        <a href='#' key={topic} onClick={(e) => props.handleClick(e, topic)}>
                            <li key={topic}>{topic}</li>
                        </a>
                    ))}
            </ul>
        </>
    )
}
