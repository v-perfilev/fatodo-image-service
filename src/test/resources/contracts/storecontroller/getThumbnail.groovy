package contracts.groupimageresource

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'get thumbnail image'
    description 'should return status 200 and file'
    request {
        method POST()
        url($(
                consumer(regex('\\/api\\/store\\/[\\w-]+\\/thumbnail')),
                producer("/api/store/group-test_filename/thumbnail")
        ))
    }
    response {
        status 200
        body(anyNonBlankString())

    }
}
