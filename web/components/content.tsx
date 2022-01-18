import { any } from 'prop-types';
import useSWR from 'swr'
import styles from '../styles/Content.module.css'

const fetcher = (url: RequestInfo) => fetch(url).then((res) => res.json())

export default function Content(props: any) {
    const { data, error } = useSWR<string, Error>(`http://localhost:8080/json/${props.topic}`, fetcher)

    if (error) return <div>Failed to load</div>
    if (!data) return <div>Loading...</div>

    return (
        <>
            <div className={styles.panel}>
                <h2>Topic: MyName</h2>
                <textarea rows="20" cols="100" defaultValue={ JSON.stringify(data, null, 4) }>
                </textarea>
            </div>
        </>
    )
}
