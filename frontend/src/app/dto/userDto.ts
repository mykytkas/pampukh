export class UserDto {
  username!: string
  pfp?: string
}

export class UserAuth extends UserDto {
  password!: string
}
