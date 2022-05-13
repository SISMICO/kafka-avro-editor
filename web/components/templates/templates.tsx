import { useState } from "react";
import styles from '../../styles/templates.module.css'
import TemplatesLoad from "./templates-load";
import TemplatesSave from "./templates-save";

export interface TemplatesProps {
    topic: string,
    message: string,
    handleClick: (template: any) => void
}

enum TemplatesState {
    None,
    Load,
    Save
}

export default function Templates(props: React.PropsWithChildren<TemplatesProps>) {
    const [state, setState] = useState(TemplatesState.None);

    const reset = () => { 
        setState(TemplatesState.None);
    }

    const loadTemplate = (template: any) => {
        reset();
        props.handleClick(template);
    }

    return (
        <>
            {state === TemplatesState.None &&
                <div className={styles.group}>
                    <button onClick={() => setState(TemplatesState.Load)}>Load Template</button>
                    <button onClick={() => setState(TemplatesState.Save)}>Save Template</button>
                </div>
            }
            {state === TemplatesState.Load &&
                <div className={styles.group}>
                    <TemplatesLoad topic={props.topic} message={props.message} handleClick={loadTemplate} onCancel={reset}></TemplatesLoad>
                </div>
            }
            {state === TemplatesState.Save &&
                <div className={styles.group}>
                    <TemplatesSave topic={props.topic} message={props.message} handleClick={reset} onCancel={reset}></TemplatesSave>
                </div>
            }
        </>
    )
}
