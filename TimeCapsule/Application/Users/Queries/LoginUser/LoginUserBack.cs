using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.Users.Queries.LoginUser
{
    // 因为不返回到前端，不叫Dto
    public class LoginUserBack
    {
        public string Username { get; set; }

        public bool IsAdmin { get; set; }

        public bool IsUser { get; set; }
    }
}
