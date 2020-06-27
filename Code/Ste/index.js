function convertKeyTo128bit(secret_key) {

    var result = "";

    if (secret_key.length() <= 16) {
        for ( i = 0; i < (16 - secret_key.length()); i++) {
            result+="#";
        }
    } else {
        result = result.substring(0, 15);
    }

    return result;
}
