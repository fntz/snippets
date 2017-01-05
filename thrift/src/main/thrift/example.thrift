namespace java com.example.thriftify

typedef i64 Age


struct Person {
  1: i64  id;
  2: required string name;
  3: optional string nickname = "anonymous";
  4: optional i64 age;
  5: binary logo;
}

const double PI = 3.14;

enum Levels {
  A;
  B;
  C;
}

exception MyException {
  1: i16 code;
  2: string message;
}

struct Containers {
  list<string> names;
  set<i64> ids;
  map<i64, string> id2name;
}


exception NotFoundException {}

enum Statuses {
  Ok,
  Fail
}

service TestService {
  string getTestData();
}

