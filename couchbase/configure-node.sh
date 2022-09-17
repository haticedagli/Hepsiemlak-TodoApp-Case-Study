set -m

 /entrypoint.sh couchbase-server &

 sleep 15

 # Setup initial cluster/ Initialize Node
 couchbase-cli cluster-init -c 127.0.0.1 --cluster-name "Hepsiemlak" --cluster-username "Administrator" \
 --cluster-password "password" --services data,index,query,fts --cluster-ramsize 256 --cluster-index-ramsize 256 \
 --cluster-fts-ramsize 256 --index-storage-setting default \

 # Setup Administrator username and password
 curl -v http://127.0.0.1:8091/settings/web -d port=8091 -d username="Administrator" -d password="password"


 sleep 15

 # Setup Bucket
 couchbase-cli bucket-create -c 127.0.0.1:8091 --username "Administrator" \
 --password "password" --bucket "Todo" --bucket-type couchbase \
 --bucket-ramsize 256

 fg 1