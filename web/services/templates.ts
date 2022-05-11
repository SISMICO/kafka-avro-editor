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

export { useGetTemplatesService };
