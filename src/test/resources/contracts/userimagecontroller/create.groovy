package contracts.userimagecontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'create user image'
    description 'should return status 201 and filename string'
    request {
        method POST()
        url("/api/user-image")
        headers {
            contentType applicationJson()
            header 'Authorization': $(
                    consumer(containing("Bearer")),
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4ZjlhN2NhZS03M2M4LTRhZDYtYjEzNS01YmQxMDliNTFkMmUiLCJ1c2VybmFtZSI6InRlc3RfdXNlciIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjowLCJleHAiOjMyNTAzNjc2NDAwfQ.Go0MIqfjREMHOLeqoX2Ej3DbeSG7ZxlL4UAvcxqNeO-RgrKUCrgEu77Ty1vgR_upxVGDAWZS-JfuSYPHSRtv-w")
            )
        }
        body(
                "filename": null,
                "content": $(
                        consumer(anyNonBlankString()),
                        producer(file("jpg-medium-square_compressed.jpg").asBytes())
                ),

        )
    }
    response {
        status 201
        body(anyNonBlankString())
    }
}
