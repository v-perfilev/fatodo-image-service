package contracts.groupimageresource

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'update group image'
    description 'should return status 200 and filename string'
    request {
        method PUT()
        url("/api/group-images")
        headers {
            contentType applicationJson()
            header 'Authorization': $(
                    consumer(containing("Bearer")),
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwidXNlcm5hbWUiOiJ0ZXN0X3VzZXIiLCJhdXRob3JpdGllcyI6IlJPTEVfVVNFUiIsImlhdCI6MCwiZXhwIjozMjUwMzY3NjQwMH0.ggV38p_Fnqo2OZNtwR3NWKZhMXPd-vf4PrRxN0NmTWsHPrKwWZJSGO2dJBBPWXWs4OI6tjsNV2TM3Kf6NK92hw")
            )
        }
        body(
                "filename": $(
                        consumer(any()),
                        producer("group-test_filename")
                ),
                "content": $(
                        consumer(any()),
                        producer(file("jpg-medium-square_compressed.jpg").asBytes())
                ),

        )
    }
    response {
        status 200
        body(anyNonBlankString())
    }
}
