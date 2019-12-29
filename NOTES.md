# NOTES

## Links

- [Android Apps – Phone Calls and SMS](https://google-developer-training.github.io/android-developer-phone-sms-course/index-book.html)

- [Detecting & sending SMS on Android ](https://android.jlelse.eu/detecting-sending-sms-on-android-8a154562597f)

- [Android - Adding at least one Activity with an ACTION-VIEW intent-filter after Updating SDK version 23](https://stackoverflow.com/questions/34367875/android-adding-at-least-one-activity-with-an-action-view-intent-filter-after-u)


- [Android permission doesn't work even if I have declared it](https://stackoverflow.com/questions/32635704/android-permission-doesnt-work-even-if-i-have-declared-it)

> java.lang.SecurityException: Sending SMS message: uid 10394 does not have android.permission.SEND_SMS

- [How to get current foreground activity context in android?](https://stackoverflow.com/questions/11411395/how-to-get-current-foreground-activity-context-in-android)

## Bootstrap App

1. create project without activity
2. add activity preferences /new file
3. add service /new file

added dependencies to `app/build.gradle`

```
dependencies {
  ...
  // added
  implementation 'io.javalin:javalin:3.6.0'
  implementation 'com.fasterxml.jackson.core:jackson-core:2.10.1'
  implementation 'com.fasterxml.jackson.core:jackson-databind:2.10.1'
  implementation 'org.apache.commons:commons-lang3:3.9'
  // lombok
  compileOnly 'org.projectlombok:lombok:1.18.10'
  annotationProcessor 'org.projectlombok:lombok:1.18.10'
}
```

## Start Service

- [How to start a service from an activity in android?](https://stackoverflow.com/questions/25717691/how-to-start-a-service-from-an-activity-in-android)

## Preferences Activity Intent Filter

- [The activity must be exported or contain an intent-filter](https://stackoverflow.com/questions/40508303/the-activity-must-be-exported-or-contain-an-intent-filter)
- 
add to `SettingsActivity`

```xml
<intent-filter>
  <action android:name="android.intent.action.MAIN" />
  <category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
```

## Use Javalin with Android

- [Running server on Android using Javalin](https://github.com/tipsy/javalin/issues/612)

More than one file was found with OS independent path `'org/eclipse/jetty/http/encoding.properties'`

fix: [More than one file was found with OS independent path 'META-INF/LICENSE'](https://stackoverflow.com/questions/44342455/more-than-one-file-was-found-with-os-independent-path-meta-inf-license)

## Use Java 8 to work with Lambdas

java 8 required for working with lambdas

fix adding `app/build.gradle`

```
  packagingOptions {
    exclude 'org/eclipse/jetty/http/encoding.properties'
  }
  compileOptions {
    targetCompatibility = 1.8
    sourceCompatibility = 1.8
  }
```

## Genymotion and IP

### Genymotion Get ip (Device)

```shell
# enter device
$ adb -s 192.168.58.101:5555 shell
# get ip
$ ifconfig eth0
eth0      Link encap:Ethernet  HWaddr 08:00:27:2c:2e:33  Driver virtio_net
          inet addr:192.168.58.101  Bcast:192.168.58.255  Mask:255.255.255.0
          inet6 addr: fe80::a00:27ff:fe2c:2e33/64 Scope: Link
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:10967 errors:0 dropped:0 overruns:0 frame:0
          TX packets:11931 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000
          RX bytes:946707 TX bytes:28579236

# check open port
netstat -tnlp | grep 7000
tcp6       0      0 :::7000                 :::*                    LISTEN      2061/network.solidary.smsgateway
```

### Scan Network (Laptop)

```shell
# scan whole network
$ sudo nmap -n -sP 192.168.58.0/24
...
MAC Address: 08:00:27:2C:2E:33 (Oracle VirtualBox virtual NIC)
Nmap scan report for 192.168.58.1
Host is up.
Nmap done: 256 IP addresses (3 hosts up) scanned in 3.59 seconds

# scan port 7000
$ sudo nmap -p 7000 192.168.58.101
Starting Nmap 7.70 ( https://nmap.org ) at 2019-12-29 19:05 WET
Nmap scan report for 192.168.58.101
Host is up (0.00057s latency).

PORT     STATE  SERVICE
7000/tcp closed afs3-fileserver
MAC Address: 08:00:27:2C:2E:33 (Oracle VirtualBox virtual NIC)

Nmap done: 1 IP address (1 host up) scanned in 7.16 seconds

# scan with mac address
$ sudo nmap -n -sP 192.168.58.0/24 | awk '/Nmap scan report/{printf $5;printf " ";getline;getline;print $3;}' | grep '08:00:27:2C:2E:33'
```

- [ADB port forwarding and reversing](https://blog.usejournal.com/adb-port-forwarding-and-reversing-d2bc71835d43)

> <http://192.168.58.1:7000> works inside device but not outside, we must forward port to host machine with

```shell
# forword port
$ adb -s 78737bdf forward tcp:7000 tcp:7000
# now it works
$ curl -X GET localhost:7000 | jq
$ curl -X POST localhost:7000 -d "{ message : 'hello' }" | jq
$ curl -s -X POST http://localhost:7000/sms -d '{ "from": "+351936202288", "to": "+351936202288", "message": "hello kapa" }'
```
