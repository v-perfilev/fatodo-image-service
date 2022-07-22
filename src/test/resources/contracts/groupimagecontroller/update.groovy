package contracts.groupimagecontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'update group image'
    description 'should return status 200 and filename string'
    request {
        method PUT()
        url("/api/group-image")
        headers {
            contentType applicationJson()
            header 'Authorization': $(
                    consumer(containing("Bearer")),
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4ZjlhN2NhZS03M2M4LTRhZDYtYjEzNS01YmQxMDliNTFkMmUiLCJ1c2VybmFtZSI6InRlc3RfdXNlciIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjowLCJleHAiOjMyNTAzNjc2NDAwfQ.Go0MIqfjREMHOLeqoX2Ej3DbeSG7ZxlL4UAvcxqNeO-RgrKUCrgEu77Ty1vgR_upxVGDAWZS-JfuSYPHSRtv-w")
            )
        }
        body(
                "filename": $(
                        consumer(anyNonBlankString()),
                        producer("group-image-filename")
                ),
                "content": $(
                        consumer(anyNonBlankString()),
                        producer(file("jpg-medium-square_compressed.jpg").asBytes())
                ),

        )
    }
    response {
        status 200
        body(anyNonBlankString())
    }
}
