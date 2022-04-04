import React from "react";

export interface IProps{
}

export interface IState {
    error?: any,
    errorInfo?: any
}

export class ErrorBoundary extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);
        this.state = {error: null, errorInfo: null};
    }

    componentDidCatch(error: any, errorInfo: any) {
        this.setState({
            error: error,
            errorInfo: errorInfo
        })
    }

    render() {
        if (this.state.errorInfo) {
            return (
                <div>
                    <h2>Something went wrong.</h2>
                    <details style={{ whiteSpace: 'pre-wrap' }}>
                        <span>Error: {this.state.error && this.state.error.toString()}</span>
                        <br />
                        <span>Stacktrace: {this.state.error.stack}</span>
                        <br />
                        <span>Component Strack: {this.state.errorInfo.componentStack}</span>
                    </details>
                </div>
            );
        }
        return this.props.children;
    }
}
