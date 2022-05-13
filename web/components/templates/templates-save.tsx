import styles from '../../styles/templates.module.css'
import { saveTemplatesService } from '../../services/templates';
import { useState } from 'react';
import { stat } from 'fs';

export interface TemplatesSaveProps {
    topic: string,
    message: string,
    handleClick: (tename: string) => void,
    onCancel: () => void
}

export default function TemplatesSave(props: React.PropsWithChildren<TemplatesSaveProps>) {
    const [state, setState] = useState('');

    const saveTemplate = () => {
        saveTemplatesService(
            props.topic,
            state,
            props.message
        );
        props.handleClick(state);
    }

    // if (service.error)
    //     throw service.error;
    // else
        return (
            <>
                <div className={styles.group}>
                    <span>Template Name:</span>
                    <input type='text' id='name' name='name' onChange={e => setState(e.target.value)}></input>
                    <button onClick={saveTemplate}>Save Template</button>
                    <button onClick={() => props.onCancel()}>Cancel</button>
                </div>
            </>
        )
}
