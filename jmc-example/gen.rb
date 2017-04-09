require 'json'

# generate around ~ 34M

arr = 1000000.times.map { |x|
  {:id => x, :name => "name-#{x}"}
}

File.open("example.json", "w") {|file| file.write(arr.to_json) }

