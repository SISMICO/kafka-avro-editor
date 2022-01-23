import type { NextPage } from 'next'
import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'
import Topics from '../components/topics'

const Home: NextPage = () => {
  return (
    <div className={styles.container}>
      <Head>
        <title>Kafka Avro Editor</title>
        <meta name="description" content="Web app to Kafka Avro Editor" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        <h1 className={styles.title}>
          Kafka Avro Editor
        </h1>
        <Topics></Topics>
      </main>

      <footer className={styles.footer}>
        <a
          href="https://github.com/SISMICO/kafka-avro-editor"
          target="_blank"
          rel="noopener noreferrer"
        >
          Powered by{' '}
          <span className={styles.logo}>
            SISMICO
          </span>
        </a>
      </footer>
    </div>
  )
}

export default Home
