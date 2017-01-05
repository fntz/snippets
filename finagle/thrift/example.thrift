namespace java com.twitter.finagle.example.thriftjava
#@namespace scala com.twitter.finagle.example.thriftscala



service MyService {
  string hi(string name);
}

service DateTimeService {
  string getDate();
}