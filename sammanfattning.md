# You can do it!

![](https://media.giphy.com/media/XreQmk7ETCak0/giphy.gif)

# Nätverksprogrammering sammanfattning

### Protokoll:

För att två program ska kunna förstå varandras meddelanden så behövs det ett gemensamt protokoll (språk).

HTTP - Är ett typsikt exempel på ett gemensamt protokoll.

### Transaktioner

Transaktioner är operationer/meddelanden som typiskt skickas från klient till server för att få en specifik operation utförd av servern.

Exempel: 
Klient skickar en begäran till servern.
Servern svarar genom att utföra specifik operation.

### Fysiskt överföringsmedium

Applikationer behöver ett medium för att kunna överföra data mellan varandra.
- H2H (Host to Host) - Omvandlar analoga och digitala signaler.

### UDP (User Datagram Protokoll)

- Upp till 65507 byte stora datagram
- Garanterar korrekta meddelanden
- Garanter **INTE** leverans
- Garanterar **INTE** inbördes ordning

### TCP (Transmission Control Protocol)

- Fast uppkoppling över nätet
- Skickar strömmar -> Data behöver ej delas upp i paket.
- Automatisk felkontroll/omsändning
- Garanterar leverans
- Garanterar inbördes ordning

### Portar

- 1-1023 - Är reserverade för standardtjänster.
- 1024-65535 - Är tillängliga för applikationer att använda.

### RFC (Request for comments)

Här defenieras protokoll standarder för internet, dessa är defenierade av IETF.

### Strömmar
- Bytes kan skrivas till strömmar.
- Bytes kan läsas från strömmar.
- En fil är en ström som lagrats i sekundärminnet.

Exempel på att läsa in 10 bytes från en inputStream:


    byte[] input = new byte[10];
    for(int i =0; i < input.length(); i++){
        int b = in.read();
        if(b == -1) break;
        input[i] = (byte) b;
    }
    
Att läsa in endast en byte i taget är ineffektivt, man brukar istället läsa in flera bytes genom att specifiera som tidigare ett byte objekt som fylls utav read funktionen, read() returnerar hur många bytes som faktiskt skrevs till byte vektorn.

    //read 100 bytes
    byte[] inputData = new byte[1024];
    int dataRead = 0;
    while(dataRead != -1 && dataRead < 100){
        dataRead = read(inputData,dataRead,inputData.length() - dataRead);
    }
    
read() returnerar -1 om det ite finns något mer att skicka.
Villkoret dataRead < 100 är till för att begränsa inläsning av 100 bytes.

#### InputStream och Outputstream
Dessa två är högst i kedjan där varje subklass till dessa bidrar till mer funktionalitet. T.e.x så har vi BufferedInputStream som bidrar till bättre prestanda vid inläsning av från fil för att vi kan buffrra data.

Exempel på hur man skapar en subbklass av dessa streams:

    InputStream is = ...;
    // Adds functionality to inputstream.
    BufferedInputStream bis = new BufferedInputStream(is);
    
Glöm ej att använda flush() vid anvädning av buffered streams, detta pga att vi buffrar data och kan t.ex. vänta på att det finns en specifik mängd data i buffereten innan vi skickar. Om vi vill vara säkra på att paketet skickas så använder vi flush().

#### Reader / Writers

Dessa används när man vill ha en teckenström istället för en byte ström som i vanliga streams. I Readers/Writers kan man då t.ex. istället använda read() för att få ett tecken, istället för att läsa en byte.

    // Same as previously with bytes, just now with characters.
    char[] characters = new char[1024];
    int dataRead = 0;
    while(dataRead != -1 && dataRead < 100){
        dataRead = reader.read(characters,dataRead, characters.length - dataRead);
    }
    
### Sockets

Glöm ej att stänga öppna sockets.

### Unicast / Multicast

#### Unicast
Här skickas kopior av samma meddelande till olika mottagare.

#### Multicast
Här används ett meddelande för att nå ut till flera mottagare, detta görs via stöd av routrar genom att man t.ex. skickar till en router som sedan skickar vidare detta meddelande till flera olika mottagare.

### Java server pages

- .jps filer är html filer med extra eleent inuti.
- jsp elementen anger vart dynamisk kod skall infogas.

Processen som sker när en klient efterfrågar en .jsp fil.

OBS: Om det är första gången filen /Hello.jps efterfrågas så sker processen 1-5, annars hoppar man över steg 2,3,4.
1. Klienten skickar GET /Hello.jsp
2. Servern läser in filen Hello.jsp
3. Genererar en Servlet, HelloServlet.java
4. Kompilerar denna java kod till html kod.
5. HTML returneras till klienten.

### Parsing HTML

Det finns två stycken html parsing kit som nämnts i kursen: HTMLEditorKit och Jsoup.

#### HTMLEditorkit:
**Advantage:**  Is a part of java, so its a standard library.
**Disadvantage:** Has limited parsing capabilities.

#### Jsoup:
**Advantage:**  Is easy to use and has high parsing capabilities.
**Disadvantage:** Is an external library which has to be imported.

#### HTML

Från HTML är det möjligt att bygga upp en trädstruktur där rooten i trädet består av HTML och alla noder till trädet är element.

    <html>
        <head>
        </head>
        
        <body>
        </body>
    </html>
    
Här har vi rooten html, följt av två noder head och body.

### Enkel beskrivning på klient-server arkitetur för strömmande media

#### Server:
Servern kan skicka ljud eller bild med en fördefenierad kodning, t.ex. MPEG med en hastighet på 25 bilder / sek och en upplösning på (1280x720).

#### Client:
Klienten tar emot mediaströmmen, avkodar denna med den fördefinierade kodningen och spelar sedan upp den. För att minska effekten av jitter så buffrar klienten en viss mängd data som lagras i en buffers innan uppspelning.


#### UDP

UDP är snabbt men har inget inbyggt stöd för felhantering och brukar ha problem med blockerande brandväggar.

Med UDP paket så tillkommer information om
- Sekvensnummer
- Tidsstämplar
- Mediatyp

UDP anses som ett okopplat för att det inte finns en förbindelse mellan två kommunikationsnoder utan varje meddelande som skickas mellan noderna är helt oberoende och därför måste destinationadressen anges explicit för varje unikt meddelande.

#### TCP 
Har inbyggt stöd för felhantering och att segmenten kommer i rätt ordning.

TCP anses som kopplat för förbindelsen mellan två kommunikationsnoder är "fast", detta gör att man inte för varje meddelande måste specifiera destination utan man har en konituerlig fast ström.

#### RTP

Realtime transport protokoll - Stödjer mediaöverföring och bygger på UDP.
Dock behövs det stöd från applikatosprogrammet för att hantera ordning på paketen samt hantering av paket förluster.

#### HTTP

Finns ett http protkoll som är baserat på TCP som är populärt.

### java.nio

I java 1.4 så kom paket som erbjöd klasserna: SocketChannel, ServerSocketChannel, Selector, med flera.

Dessa möjliggör det för t.ex. en server att endast ha en tråd för att bevaka inkommande trafik på flera uppkopplingar.

#### Selector:
Denna klass används för att registrera ett antal sockets som man vill bevaka. Genom att anropa select() på objektet så kan man sedan blockera fram tills att det går att utföra någon typ av I/O operation på de bevakade uppkopplingarna.
    
### Exectuors

Skapa en tråd pool med max antalet trådar till (3) och som startar en given tråd klass som implementerar Runnable.

    ExecutorService pool = Executors.newFixedThreadPool(3);
    pool.submit(new Thread(new Runnable()));
    
Om man vill ha en tråd som returnerar ett värde ska klassen istället implementera Callable<value> och överlagra metoden call() istälelt för run().

Exempel:

    // Thread class
    public class myCallable implements Callable<String>{
         public String call(){
            return "hejsan";
        }
    }
    
    // Main class
    public class main{
        public static void main (String[]args){
            ExecutorService pool = Executors.newFixedThreadPool(3);
            Future<String> temp = pool.submit(new Thread(new Runnable()));
            // Temp stores the future, however get() method is 
            // blocking until the value is retrieved.
            System.out.print("
            Call temp.get(), this method is blocking until a  value is returned"
            +temp.get());
        }
    }
    
### RTP (Realtime Transport Protokoll)

Är ett protokoll som är avsett för ljud och bild i realtid, dock garanterar det inte realtidsöverföring. Protokollet är paketbaserat och innehåller indetifikation av:
- Mediatyp
- Tidsstämplar
- Sekvensnummer

Kräver dock ett tillägg från applikationslagret (nämnt tidigare) för att kunna hantera paket som ej kommer i rätt ordning, jitter samt kompensation av paketförluster.

RTP grundar sig på UDP.

### RTCP (Realtime Control Protocol)
RTCP är en del av RTP protokollet och används för att förmedla statistik över förbindelsen mellan användare och mottagare, statistik som finns är:

- Jitter
- Paketförluter
- Antal sända paket

### RTSP (Realtime Streaming protokoll)

Är ett HTTP liknande protokoll som används för att kunna kontrollera uppspelning av strömmande media likt en fjärkontroll, exempel: start, stop och pausa.
    
