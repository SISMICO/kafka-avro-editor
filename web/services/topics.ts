import { useEffect, useState } from "react";

export interface TopicsResponse {
    result?: string[],
    error?: Error
}

const useGetTopicsService = (filter: string) => {
    const [result, setResult] = useState<TopicsResponse>({});

    useEffect(() => {
        fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/topics`, { method: 'GET' })
            .then(data => data.json())
            .then(data => data?.filter((item: string) => item.indexOf(filter) >= 0))
            .then(data => setResult({ result: data }))
            .catch((error: any) => setResult({ error: error }))
    }, [filter]);

    return result;
}

export interface JsonTopicResponse {
    result?: string,
    error?: Error
}

const useGetJsonTopicService = (topic: string) => {
    const [result, setResult] = useState<TopicsResponse>({});

    useEffect(() => {
        fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/json/${topic}`, { method: 'GET' })
            .then(data => data.json())
            .then(data => setResult({ result: data }))
            .catch((error: any) => setResult({ error: error }))
    }, [topic]);

    return result;
}

export interface SendEventResponse {
    result?: string,
    error?: Error
}

const sendEvent = async (topic: string, message: string): Promise<SendEventResponse> => {
    let response: SendEventResponse = {};

    await fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/send/${topic}`, {
        method: 'post',
        body: message
    })
        .then(data => {
            if (!data.ok) throw new Error(`Error to send event: ${data.status} - ${data.statusText}`);
            return data.text();
        })
        .then(data => response = { result: data })
        .catch((error: any) => response = { error: error })
    return response;
}

export { useGetJsonTopicService, useGetTopicsService, sendEvent };
