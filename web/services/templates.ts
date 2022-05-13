import { useEffect, useState } from "react";
import Template from "../models/template";

export interface TemplateResponse {
    result?: Template[],
    error?: Error
}

const useGetTemplatesService = (topic: string) => {
    const [result, setResult] = useState<TemplateResponse>({});

    useEffect(() => {
        fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/examples/${topic}`, { method: 'GET' })
            .then(data => data.json())
            .then(data => setResult({ result: data }))
            .catch((error: any) => setResult({ error: error }))
    }, [topic]);

    return result;
}

const saveTemplatesService = (topic: string, name: string, template: string) => {
    let result = { result: null, error: null }

    fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/examples`, {
        method: 'PUT',
        body: JSON.stringify({
            topic: topic,
            name: name,
            example: template
        }),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(data => data.json())
        .then(data => result.result = data.json())
        .catch((error: any) => result.error = error)

    return result;
}

export { useGetTemplatesService, saveTemplatesService };
