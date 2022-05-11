import { useState } from 'react'
import Template from '../models/template';
import styles from '../styles/templates.module.css'
import { useGetTemplatesService } from '../services/templates';

interface TemplatesProps {
    topic: string,
    handleClick: (template: any) => void
}

export default function Templates(props: React.PropsWithChildren<TemplatesProps>) {
    const [state, setState] = useState({});
    const service = useGetTemplatesService(props.topic);

    const selectTemplate = (event: React.ChangeEvent<HTMLSelectElement>) => {
        let value = event.target.value;
        if (value != '')
            setState(JSON.parse(value));
        else
            setState({});
    }

    if (service.error)
        throw service.error;
    else
        return (
            <>
                <div className={styles.group}>
                    <span>Templates:</span>
                    <select onChange={selectTemplate} defaultValue={''}>
                        <option key={''} value={''}></option>
                        {service?.result?.map(
                            (template: Template) => <option key={template.name} value={template.example}>{template.name}</option>
                        )}
                    </select>
                    <button onClick={() => props.handleClick(state)}>Load Template</button>
                </div>
            </>
        )
}
