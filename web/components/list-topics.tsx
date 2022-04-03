import { useGetTopicsService } from '../services/topics';

interface ListTopicsProps {
    filter: string,
    handleClick: (event: any, topic: string) => void
}

export default function ListTopics(props: React.PropsWithChildren<ListTopicsProps>) {
    const service = useGetTopicsService(props.filter);

    if (service.error)
        throw service.error;
    else
        return (
            <>
                <ul>
                    {service?.result?.map(
                        (topic: string) => (
                            <a href='#' key={topic} onClick={(e) => props.handleClick(e, topic)}>
                                <li key={topic}>{topic}</li>
                            </a>
                        ))}
                </ul>
            </>
        );
}
